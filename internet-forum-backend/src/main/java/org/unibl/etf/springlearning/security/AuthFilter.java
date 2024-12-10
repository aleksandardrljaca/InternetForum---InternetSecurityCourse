package org.unibl.etf.springlearning.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.unibl.etf.springlearning.models.dto.JwtUser;
import org.unibl.etf.springlearning.models.dto.Permission;
import org.unibl.etf.springlearning.models.enums.Role;
import org.unibl.etf.springlearning.services.TokenBlackListEntityService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
public class AuthFilter extends OncePerRequestFilter {
    @Value("${authorization.token.header.name}")
    private String authorizationHeaderName;
    @Value("${authorization.token.header.prefix}")
    private String authorizationHeaderPrefix;
    @Value("${authorization.token.secret}")
    private String authorizationSecret;
    private final TokenBlackListEntityService tokenBlackListEntityService;

    public AuthFilter(TokenBlackListEntityService tokenBlackListEntityService) {
        this.tokenBlackListEntityService = tokenBlackListEntityService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = httpServletRequest.getHeader(authorizationHeaderName);
        if (authorizationHeader == null || !authorizationHeader.startsWith(authorizationHeaderPrefix)) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }
        String token = authorizationHeader.replace(authorizationHeaderPrefix, "");
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(authorizationSecret)))
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
            if(claims.getExpiration().before(new Date())){
                logger.error("JWT token has expired! ");
                httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
            else if(tokenBlackListEntityService.isOnBlackList(token)){
                logger.error("JWT token is blacklisted! ");
                httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
            List<Map<String, Object>> permissionsData = claims.get("permissions", List.class);

            List<Permission> permissions = new ArrayList<>();
            for (Map<String, Object> permMap : permissionsData) {
                String type = (String) permMap.get("type");
                Integer userId = (Integer) permMap.get("userByUserIdId");
                permissions.add(new Permission(type, userId));
            }
            JwtUser jwtUser = new JwtUser(Integer.valueOf(claims.getId()), claims.getSubject(), null, Role.valueOf(claims.get("role", String.class)),permissions);
            logger.info("User ID: " + jwtUser.getId() + ", Permissions: " + jwtUser.getPermissions());
            Authentication authentication = new UsernamePasswordAuthenticationToken(jwtUser, null, jwtUser.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception e) {
            logger.error(e.getMessage());
            logger.error("JWT Authentication failed from: " + httpServletRequest.getRemoteHost());
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
