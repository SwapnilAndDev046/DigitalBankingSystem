package com.swapnil.bankmanagement.Service.Impl;

import com.swapnil.bankmanagement.Dto.LoginRequestDto;
import com.swapnil.bankmanagement.Dto.LoginResponseDto;
import com.swapnil.bankmanagement.Dto.SignupRequestDto;
import com.swapnil.bankmanagement.Dto.SignupResponseDto;
import com.swapnil.bankmanagement.Entity.AppUser;
import com.swapnil.bankmanagement.Exception.UserAlreadyExists;
import com.swapnil.bankmanagement.Repository.UserRepository;
import com.swapnil.bankmanagement.Security.JwtService;
import com.swapnil.bankmanagement.Service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Transactional
    @Override
    public SignupResponseDto signup(SignupRequestDto signupRequestDto) {
        AppUser appUser = userRepository.findByEmail(signupRequestDto.getEmail());

        if (appUser != null)
            throw new UserAlreadyExists("User Already Exists");

        AppUser appUser1 = modelMapper.map(signupRequestDto, AppUser.class);
        appUser1.setPassword(passwordEncoder.encode(signupRequestDto.getPassword()));

        AppUser savedUser = userRepository.save(appUser1);

        return new SignupResponseDto(savedUser.getEmail()+" User Got Created");
    }



    @Transactional
    @Override
    public LoginResponseDto login(LoginRequestDto loginRequestDto) {

        //Authenticate the user - means crosscheck the username+password
        //email + password -> AuthenticationManager -> DaoAuthenticationProvider(AuthProvider )
        // -> CustomUserDetailsService -> UserRepository -> User found?
        // -> PasswordEncoder checks password -> Authentication SUCCESS
        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                loginRequestDto.getEmail(),
                                loginRequestDto.getPassword()
                        )
                );

        //.getPrincipal() returns primary identity of authenticated user
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String token = jwtService.generateToken(userDetails); // this will generate the token

        return new LoginResponseDto(token);


//        // session creation for authenticated user
//        SecurityContext context =
//                SecurityContextHolder.createEmptyContext();
//
//        context.setAuthentication(authentication);
//
//        SecurityContextHolder.setContext(context);
//
//        //create http session - keep authentication persistent
//       Store this authenticated user's SecurityContext inside this HTTP session.
//        request.getSession(true)
//                .setAttribute(
//                        "SPRING_SECURITY_CONTEXT",
//                        context
//                );
    }
}
