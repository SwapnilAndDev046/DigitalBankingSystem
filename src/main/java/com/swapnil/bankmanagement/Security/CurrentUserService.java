package com.swapnil.bankmanagement.Security;

import com.swapnil.bankmanagement.Entity.AppUser;
import com.swapnil.bankmanagement.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CurrentUserService {

    private final UserRepository userRepository;

    public AppUser getCurrentUser() {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new IllegalStateException("User is not authenticated");
        }

        String email = authentication.getName();

        AppUser user = userRepository.findByEmail(email);

        if (user == null) {
            throw new IllegalStateException("Authenticated user not found");
        }

        return user;
    }
}