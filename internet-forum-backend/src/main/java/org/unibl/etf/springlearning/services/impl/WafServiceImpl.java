package org.unibl.etf.springlearning.services.impl;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.unibl.etf.springlearning.exceptions.UnauthorizedException;
import org.unibl.etf.springlearning.services.SiemEntityService;
import org.unibl.etf.springlearning.services.TokenBlackListEntityService;
import org.unibl.etf.springlearning.services.WafService;
@Service
public class WafServiceImpl implements WafService {
    private final SiemEntityService siemService;
    private final HttpServletRequest httpServletRequest;
    private final TokenBlackListEntityService jwtTokenService;
    @Value("${authorization.token.header.name}")
    private String authorizationHeaderName;
    @Value("${authorization.token.header.prefix}")
    private String authorizationHeaderPrefix;

    public WafServiceImpl(SiemEntityService siemService, HttpServletRequest httpServletRequest, TokenBlackListEntityService jwtTokenService) {
        this.siemService = siemService;
        this.httpServletRequest = httpServletRequest;
        this.jwtTokenService = jwtTokenService;
    }

    @Override
    public void check(BindingResult bindingResult) throws UnauthorizedException {
        if(!bindingResult.hasErrors())
            return;
        StringBuilder stringBuilder=new StringBuilder();
        stringBuilder.append("Malicious request registered!!!").append(System.lineSeparator());
        bindingResult.getFieldErrors().forEach(fieldError -> {
            stringBuilder.append("Field: ").append(fieldError.getField()).append(" | Error: ")
                    .append(fieldError.getDefaultMessage()).append(System.lineSeparator());
        });
        siemService.log(stringBuilder.toString());
        String authorizationHeader = httpServletRequest.getHeader(authorizationHeaderName);
        String token=null;
        if (authorizationHeader != null && authorizationHeader.startsWith(authorizationHeaderPrefix))
                token = authorizationHeader.replace(authorizationHeaderPrefix, "");
        if(token!=null)
            jwtTokenService.blacklist(token);
        throw new UnauthorizedException();
    }
}
