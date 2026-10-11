
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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity httpSecurity) throws Exception {

        httpSecurity
                // Enable CORS for requests from the React frontend
                .cors(cors ->
                        cors.configurationSource(corsConfigurationSource())
                )

                .csrf(csrf -> csrf.disable())

                // JWT authentication: no server-side HTTP session
                .sessionManagement(sessionConfig ->
                        sessionConfig.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        )
                )

                .authorizeHttpRequests(auth -> auth

                        // Public endpoints
                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/v1/register",
                                "/api/v1/login"
                        ).permitAll()

                        // Branches: CUSTOMER or ADMIN
                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/v1/branches/all"
                        ).hasAnyRole("CUSTOMER", "ADMIN")

                        // ADMIN-only GET endpoints
                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/v1/customers/all",
                                "/api/v1/customers/pagination",
                                "/api/v1/accounts/all"
                        ).hasRole("ADMIN")

                        // ADMIN: create branch
                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/v1/branches"
                        ).hasRole("ADMIN")

                        // ADMIN: update accounts and branches
                        .requestMatchers(
                                HttpMethod.PUT,
                                "/api/v1/accounts/*",
                                "/api/v1/branches/*"
                        ).hasRole("ADMIN")

                        // ADMIN: partially update branches
                        .requestMatchers(
                                HttpMethod.PATCH,
                                "/api/v1/branches/*"
                        ).hasRole("ADMIN")

                        // ADMIN: delete customers, accounts and branches
                        .requestMatchers(
                                HttpMethod.DELETE,
                                "/api/v1/customers/*",
                                "/api/v1/accounts/*",
                                "/api/v1/branches/*"
                        ).hasRole("ADMIN")

                        // CUSTOMER: account creation and transactions
                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/v1/accounts/create",
                                "/api/v1/transactions/deposit",
                                "/api/v1/transactions/withdraw",
                                "/api/v1/transactions/transfer"
                        ).hasRole("CUSTOMER")

                        // CUSTOMER: update their details
                        .requestMatchers(
                                HttpMethod.PUT,
                                "/api/v1/customers/edits"
                        ).hasRole("CUSTOMER")

                        .requestMatchers(
                                HttpMethod.PATCH,
                                "/api/v1/customers/edit"
                        ).hasRole("CUSTOMER")

                        // CUSTOMER: their accounts, balance and history
                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/v1/accounts/my-accounts",
                                "/api/v1/accounts/*/balance",
                                "/api/v1/transactions/*/history"
                        ).hasRole("CUSTOMER")

                        // All other endpoints require authentication
                        .anyRequest().authenticated()
                )

                // Keep your existing JWT authentication filter
                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return httpSecurity.build();
    }

    /**
     * CORS configuration for the React + Vite frontend.
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(
                List.of("http://localhost:5173")
        );

        configuration.setAllowedMethods(
                List.of(
                        "GET",
                        "POST",
                        "PUT",
                        "PATCH",
                        "DELETE",
                        "OPTIONS"
                )
        );

        configuration.setAllowedHeaders(
                List.of(
                        "Authorization",
                        "Content-Type",
                        "Accept"
                )
        );

        // JWT is sent through the Authorization header,
        // not through cookie-based authentication.
        configuration.setAllowCredentials(false);

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**", configuration);

        return source;
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
