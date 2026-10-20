package com.swapnil.bankmanagement.Controller;

import com.swapnil.bankmanagement.Dto.*;
import com.swapnil.bankmanagement.Service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @GetMapping("/auth/me")
    public ResponseEntity<CurrentUserResponse> currentLogin(){
        return ResponseEntity.ok(authService.currentLogin());
    }

    @PostMapping("/register")
    public ResponseEntity<SignupResponseDto> signup(@RequestBody @Valid SignupRequestDto signupRequestDto) {
        return ResponseEntity.ok(authService.signup(signupRequestDto));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody @Valid LoginRequestDto loginRequestDto) {
        return ResponseEntity.ok(authService.login(loginRequestDto));
    }
}
