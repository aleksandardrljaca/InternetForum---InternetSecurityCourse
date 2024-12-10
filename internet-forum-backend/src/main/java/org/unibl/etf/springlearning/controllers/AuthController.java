package org.unibl.etf.springlearning.controllers;

import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.unibl.etf.springlearning.exceptions.NotFoundException;
import org.unibl.etf.springlearning.exceptions.UnauthorizedException;
import org.unibl.etf.springlearning.models.dto.*;
import org.unibl.etf.springlearning.services.AuthService;
import org.unibl.etf.springlearning.services.WafService;

import java.io.IOException;

@RestController
@RequestMapping("/api")
public class AuthController {
    private final AuthService authService;
    private final WafService wafService;

    public AuthController(AuthService authService, WafService wafService) {
        this.authService = authService;
        this.wafService = wafService;
    }

    @PostMapping("/sign-up")
    public User signUp(@Valid @RequestBody UserRegistrationRequest request, BindingResult bindingResult)throws UnauthorizedException{
        wafService.check(bindingResult);
        return authService.signUp(request);
    }

    @PostMapping("/login")
    public Integer login(@Valid @RequestBody LoginRequest request, BindingResult bindingResult) throws UnauthorizedException{
        wafService.check(bindingResult);
        return authService.login(request);
    }
    @PostMapping("/login/verify")
    public LoginResponse secondStepLogin(@Valid @RequestBody VerificationRequest request, BindingResult bindingResult) throws UnauthorizedException {
        wafService.check(bindingResult);
        return authService.secondStepLogin(request);
    }
    @GetMapping("/github-login/{code}")
    public LoginResponse getUserInfo(@PathVariable String code) throws IOException, InterruptedException, NotFoundException {
        return authService.oauth2Login(code);
    }
}
