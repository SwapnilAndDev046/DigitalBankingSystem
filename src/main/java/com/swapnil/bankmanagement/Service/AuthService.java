package com.swapnil.bankmanagement.Service;

import com.swapnil.bankmanagement.Dto.LoginRequestDto;
import com.swapnil.bankmanagement.Dto.LoginResponseDto;
import com.swapnil.bankmanagement.Dto.SignupRequestDto;
import com.swapnil.bankmanagement.Dto.SignupResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {

    SignupResponseDto signup(SignupRequestDto signupRequestDto);
    LoginResponseDto login(LoginRequestDto loginRequestDto);
}
