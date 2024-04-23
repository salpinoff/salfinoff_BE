package com.server.salpinoffServer.infra.auth.dto;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;

@Getter
public class UserInfo {

    private final Long userId;

    public UserInfo(Long userId) {
        this.userId = userId;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(() -> "USER");
        return authorities;
    }
}
