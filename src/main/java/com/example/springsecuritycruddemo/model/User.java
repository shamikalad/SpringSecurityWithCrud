package com.example.springsecuritycruddemo.model;

import com.mysql.cj.protocol.ColumnDefinition;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String username;
    private String password;
    private String role;
    private boolean enabled;
    @Column(columnDefinition = " LONGBLOB")
    private byte[] userProfile;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<Authority> authority=new HashSet<>();
        Authority auth=new Authority();
        auth.setAuthority(role);
        authority.add(auth);
        return authority;

    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
