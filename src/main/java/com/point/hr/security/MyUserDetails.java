package com.point.hr.security;

import com.point.hr.entity.User;
import com.point.hr.entity.UserRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class MyUserDetails implements UserDetails {
    private final User user;

    public MyUserDetails(User user, UserRole userRole) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // INFO: Spring Security expects "ROLE_"
        return user.getUserRoles().stream()
                .map(ur -> {
                    String roleName = ur.getRole().getName();

                    if (!roleName.startsWith("ROLE_")) {
                        roleName = "ROLE_" + roleName;
                    }

                    return new SimpleGrantedAuthority(roleName);
                })
                .toList();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    // INFO: Spring Security's unique login identifier
    public String getUsername() {
        return user.getId().toString();
    }

    public int getUserId() {
        return user.getId();
    }

    public String getFullName() {
        return user.getPerson().getFirstName() + " " + user.getPerson().getLastName();
    }
}
