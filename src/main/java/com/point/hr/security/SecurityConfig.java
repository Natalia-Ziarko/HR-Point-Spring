package com.point.hr.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        HttpSessionRequestCache requestCache = new HttpSessionRequestCache();
        requestCache.setMatchingRequestParameterName("continue");

        http.authorizeHttpRequests(
                config -> config
                .requestMatchers("/css/**", "/favicon.ico").permitAll() // INFO: Allow static resources
                .anyRequest().authenticated() // INFO: Secure other endpoints
        ).formLogin(
                form -> form
                .loginPage("/showLoginPage")
                .loginProcessingUrl("/authenticateTheUser")
                .defaultSuccessUrl("/home")
                .permitAll()
        ).logout(LogoutConfigurer::permitAll
        ).requestCache((cache) -> cache.requestCache(requestCache)
        );

        return http.build();
    }
}
