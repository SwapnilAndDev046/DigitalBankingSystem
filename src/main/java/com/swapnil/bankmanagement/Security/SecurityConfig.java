package com.swapnil.bankmanagement.Security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity
                .csrf(csrf -> csrf.disable())

                .sessionManagement(sessionConfig ->
                        sessionConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .authorizeHttpRequests(auth -> auth

                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/v1/register",
                                "/api/v1/login"
                        ).permitAll()



                        .requestMatchers(
                                HttpMethod.DELETE,
                                "/api/v1/customers/**",
                                "/api/v1/accounts/**",
                                "/api/v1/branches/**"
                        ).hasAnyRole("ADMIN")

                        .requestMatchers(
                                HttpMethod.PUT,
                                "/api/v1/accounts/**",
                                "/api/v1/branches/**"
                        ).hasRole("ADMIN")

                        .requestMatchers(
                                HttpMethod.PATCH,
                                "/api/v1/branches/**"
                        ).hasRole("ADMIN")


                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/v1/branches/all"
                        ).hasAnyRole("CUSTOMER","ADMIN")

                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/v1/customers/all",
                                "/api/v1/customers/pagination",
                                "/api/v1/accounts/all",
                                "/api/v1/accounts/account"
                        ).hasAnyRole("ADMIN")

                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/v1/transactions/deposit",
                                "/api/v1/transactions/withdraw",
                                "/api/v1/transactions/transfer",
                                "/api/v1/accounts/create"
                        ).hasRole("CUSTOMER")

                        .requestMatchers(
                                HttpMethod.PUT,
                                "/api/v1/customers/edits"
                        ).hasRole("CUSTOMER")

                        .requestMatchers(
                                HttpMethod.PATCH,
                                "/api/v1/customers/edit"
                        ).hasRole("CUSTOMER")

                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/v1/branches"
                        ).hasRole("ADMIN")

                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/v1/accounts/*/balance",
                                "/api/v1/transactions/*/history",
                                "/api/v1/accounts/my-accounts"
                        ).hasRole("CUSTOMER")

                        .anyRequest().authenticated()
                )

                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return httpSecurity.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(
            UserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder) {

        DaoAuthenticationProvider provider =
                new DaoAuthenticationProvider();

        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);

        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationProvider authenticationProvider) {

        return new ProviderManager(authenticationProvider);
    }
}