package com.swapnil.bankmanagement.Controller;

import com.swapnil.bankmanagement.Dto.*;
import com.swapnil.bankmanagement.Service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.RequestEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @GetMapping("/auth/me")
    public CurrentUserResponse currentLogin(){
        return authService.currentLogin(); 
    }

    @PostMapping("/register")
    public SignupResponseDto signup(@RequestBody @Valid SignupRequestDto signupRequestDto) {
        return authService.signup(signupRequestDto);
    }

    @PostMapping("/login")
    public LoginResponseDto login(@RequestBody @Valid LoginRequestDto loginRequestDto) {
        return authService.login(loginRequestDto);
    }
}
