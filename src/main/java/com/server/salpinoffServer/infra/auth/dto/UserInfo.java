package com.server.salpinoffServer.infra.auth.dto;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;

@Getter
public class UserInfo {

    private final Long userId;
    private final String authority;

    public UserInfo(Long userId, String authority) {
        this.userId = userId;
        this.authority = authority;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(() -> authority);
        return authorities;
    }
}
