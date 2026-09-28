package com.swapnil.bankmanagement.Security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain
    ) throws ServletException, IOException {

        //Get Authorization header
        String authHeader = request.getHeader("Authorization");

        //If there is no Bearer token, continue normally
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);//move to next thing....
            return;
        }

        //Remove "Bearer " and keep only the JWT
        String token = authHeader.substring(7);

        try {

            //Extract email from JWT
            String username = jwtService.extractUsername(token);

            //Only authenticate if no authentication already exists
            if (username != null
                    && SecurityContextHolder.getContext().getAuthentication() == null) {

                //Load user from database
                UserDetails userDetails =
                        userDetailsService.loadUserByUsername(username);


                //Check whether JWT is valid for this user
                if (jwtService.validateToken(token, userDetails)) {

                    //Create Spring Security Authentication
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null,
                                    userDetails.getAuthorities()
                            );

                    //Put authentication into SecurityContext
                    SecurityContextHolder
                            .getContext()
                            .setAuthentication(authentication);
                }
            }

            //Continue the request
            filterChain.doFilter(request, response);

        } catch (Exception exception) {
            exception.printStackTrace();
            response.sendError(
                    HttpServletResponse.SC_UNAUTHORIZED,
                    "Invalid or expired JWT"
            );
        }
    }
}