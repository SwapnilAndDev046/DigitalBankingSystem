package com.swapnil.bankmanagement.Service;

import com.swapnil.bankmanagement.Dto.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {

    SignupResponseDto signup(SignupRequestDto signupRequestDto);
    LoginResponseDto login(LoginRequestDto loginRequestDto);
    CurrentUserResponse currentLogin();
}
