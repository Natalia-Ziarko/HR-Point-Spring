package com.point.hr.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;

import javax.sql.DataSource;

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

    @Bean
    public UserDetailsManager userDetMan(DataSource dataSource) {
        JdbcUserDetailsManager theUserDetMan = new JdbcUserDetailsManager(dataSource);

        theUserDetMan.setUsersByUsernameQuery("select userId, userPw, userIfActive from users where userId=?");

        theUserDetMan.setAuthoritiesByUsernameQuery("select userId, urRoleId from users left join usersRoles on urUserId=userId where userId=?");

        return theUserDetMan;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
