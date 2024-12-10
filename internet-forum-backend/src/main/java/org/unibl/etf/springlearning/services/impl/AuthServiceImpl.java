package org.unibl.etf.springlearning.services.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.modelmapper.ModelMapper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import org.springframework.stereotype.Service;
import org.unibl.etf.springlearning.exceptions.NotFoundException;
import org.unibl.etf.springlearning.exceptions.UnauthorizedException;
import org.unibl.etf.springlearning.models.dto.*;
import org.unibl.etf.springlearning.repositories.UserEntityRepository;
import org.unibl.etf.springlearning.services.AuthService;
import org.unibl.etf.springlearning.services.EmailService;
import org.unibl.etf.springlearning.services.UserEntityService;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.Date;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;


@Service
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final UserEntityService userEntityService;
    private final ModelMapper modelMapper;
    private final EmailService emailService;
    private final UserEntityRepository userRepository;

    @Value("${authorization.token.expiration-time}")
    private String tokenExpirationTime;
    @Value("${authorization.token.secret}")
    private String tokenSecret;
    @Value("${github.access.token.url}")
    private String accessTokenUrl;
    @Value("${github.user.emails.url}")
    private String userEmailsUrl;

    public AuthServiceImpl(AuthenticationManager authenticationManager, UserEntityService userEntityService, ModelMapper modelMapper, EmailService emailService, UserEntityRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.userEntityService = userEntityService;
        this.modelMapper = modelMapper;
        this.emailService = emailService;
        this.userRepository = userRepository;
    }
    @Override
    public User signUp(UserRegistrationRequest request){
        return userEntityService.insert(request);
    }

    @Override
    public Integer login(LoginRequest request) throws UnauthorizedException {
        Authentication authentication=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(),request.getPassword()));
        JwtUser jwtUser= (JwtUser) authentication.getPrincipal();
        User user=userEntityService.findById(jwtUser.getId());
        if(user.getRestricted() || !user.getVerified()) throw new UnauthorizedException();
        user.setVerificationCode(generateVerificationCode());
        emailService.send(user.getEmail(),"Verification code",user.getVerificationCode());
        userEntityService.updateVerificationCode(user.getId(),user.getVerificationCode());

        return user.getId();
    }

    @Override
    public LoginResponse secondStepLogin(VerificationRequest request) throws UnauthorizedException{
        User user=userEntityService.findById(request.getUserId());
        boolean verified=verifyCode(user.getVerificationCode(),request.getCode());
        if(!verified) throw new UnauthorizedException();
        LoginResponse response=modelMapper.map(user,LoginResponse.class);
        response.setToken(generateJwt(modelMapper.map(user,JwtUser.class)));
        return response;
    }
    @Override
    public LoginResponse oauth2Login(String code) throws IOException, InterruptedException, NotFoundException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest tokenRequest = HttpRequest.newBuilder()
                .uri(URI.create(accessTokenUrl+code))
                .build();
        HttpResponse<String> tokenResponse = client.send(tokenRequest, HttpResponse.BodyHandlers.ofString());
        String access_token = tokenResponse.body().substring(13, tokenResponse.body().indexOf('&'));

        HttpRequest userInfoRequest = HttpRequest.newBuilder()
                .uri(URI.create(userEmailsUrl))
                .header("Authorization", "Bearer " + access_token)
                .build();
        HttpResponse<String> userInfo = client.send(userInfoRequest, HttpResponse.BodyHandlers.ofString());

        LoginResponse loginResponse;
        List<String> userEmails = getUserEmails(userInfo.body());
        for (String email : userEmails) {
            if (userRepository.existsUserEntityByEmail(email)) {
                User user = modelMapper.map(userRepository.findUserEntityByEmail(email), User.class);
                loginResponse = modelMapper.map(user, LoginResponse.class);
                loginResponse.setToken(generateJwt(modelMapper.map(user, JwtUser.class)));
                return loginResponse;
            }
        }
        throw new NotFoundException();
    }

    private String generateJwt(JwtUser user) {
        return Jwts.builder()
                .id(user.getId().toString())
                .claim("role", user.getRole().name())
                .claim("permissions",user.getPermissions())
                .subject(user.getId().toString())
                .expiration(new Date(System.currentTimeMillis() + Long.parseLong(tokenExpirationTime)))
                .signWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(tokenSecret)))
                .compact();
    }
    private String generateVerificationCode(){
        Random r=new Random();
        return String.valueOf(r.nextInt(999999-100000)+100000);
    }
    private boolean verifyCode(String userCode,String input){
        boolean isSixDigit= Pattern.matches("\\d+",input);
        if(!isSixDigit) return false;
        return userCode.equals(input);
    }
    private List<String> getUserEmails(String response) throws NotFoundException {
        ObjectMapper mapper = new ObjectMapper();
        List<String> emailList;
        try {
            List<GitHubUserEmail> emails = mapper.readValue(response, new TypeReference<>() {
            });
            emailList = emails.stream().map(GitHubUserEmail::getEmail).toList();

        } catch (IOException e) {
            throw new NotFoundException();
        }
        return emailList;
    }
}
