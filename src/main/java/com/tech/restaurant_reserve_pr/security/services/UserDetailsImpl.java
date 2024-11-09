package com.tech.restaurant_reserve_pr.security.services;



import com.tech.restaurant_reserve_pr.model.User;

import org.springframework.security.core.GrantedAuthority;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;


public class UserDetailsImpl implements UserDetails {

    private User user;

    public UserDetailsImpl(User user) {
        this.user = user;
    }

    @Override
    public String getUsername() {
        // Devuelve el correo electrónico del usuario como username
        return user.getEmail();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Si no estás usando roles, puedes retornar una lista vacía
        return Collections.emptyList();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
