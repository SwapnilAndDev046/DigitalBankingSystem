package com.swapnil.bankmanagement.Controller;

import com.swapnil.bankmanagement.Dto.LoginRequestDto;
import com.swapnil.bankmanagement.Dto.LoginResponseDto;
import com.swapnil.bankmanagement.Dto.SignupRequestDto;
import com.swapnil.bankmanagement.Dto.SignupResponseDto;
import com.swapnil.bankmanagement.Service.AuthService;
import com.swapnil.bankmanagement.Service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.RequestEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public SignupResponseDto signup(@RequestBody SignupRequestDto signupRequestDto) {
        return authService.signup(signupRequestDto);
    }

    @PostMapping("/login")
    public LoginResponseDto login(@RequestBody LoginRequestDto loginRequestDto) {
        return authService.login(loginRequestDto);
    }
}
