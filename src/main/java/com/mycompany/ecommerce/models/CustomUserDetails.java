package com.mycompany.ecommerce.models;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class CustomUserDetails implements UserDetails {

    private String doc;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetails(Usuario user) {
        this.doc = user.getDoc(); // Usando o documento como nome de usuário
        this.password = user.getPassword();
        this.authorities = user.getRole();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.doc;
    }
}
