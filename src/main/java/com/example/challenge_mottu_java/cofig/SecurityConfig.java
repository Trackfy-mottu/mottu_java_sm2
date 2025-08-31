package com.example.challenge_mottu_java.cofig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    @Autowired
    private AuthFilter authFilter;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST, "bike").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "bike").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "bike").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "pendings").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "pendings").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "pendings").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/user").permitAll()
                        .requestMatchers(HttpMethod.POST, "/user/form").permitAll()
                        .requestMatchers(HttpMethod.GET, "/user/form").permitAll()
                        .requestMatchers(HttpMethod.POST, "/court/form").permitAll()
                        .requestMatchers(HttpMethod.GET, "/court/form").permitAll()
                        .requestMatchers(HttpMethod.POST, "/login").permitAll()
                        .anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults())
                .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)
                .csrf(csrf -> csrf.disable())
                .build();
    }


    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}