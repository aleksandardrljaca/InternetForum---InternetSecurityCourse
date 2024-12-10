package org.unibl.etf.springlearning.services;


import org.springframework.stereotype.Service;
import org.unibl.etf.springlearning.exceptions.NotFoundException;
import org.unibl.etf.springlearning.exceptions.UnauthorizedException;
import org.unibl.etf.springlearning.models.dto.*;

import java.io.IOException;

public interface AuthService {
    User signUp(UserRegistrationRequest request);
    LoginResponse oauth2Login(String code) throws IOException, InterruptedException, NotFoundException;
    Integer login(LoginRequest request) throws UnauthorizedException;
    LoginResponse secondStepLogin(VerificationRequest request) throws UnauthorizedException;

}
