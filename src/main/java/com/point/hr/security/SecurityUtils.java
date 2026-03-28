package com.point.hr.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtils {

    public MyUserDetails getLoggedInUser() {
        return (MyUserDetails) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
    }

    public Integer getLoggedInUserId() {
        return getLoggedInUser().getUserId();
    }

}