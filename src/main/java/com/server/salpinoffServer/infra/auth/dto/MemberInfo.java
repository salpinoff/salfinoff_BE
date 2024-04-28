package com.server.salpinoffServer.infra.auth.dto;

import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;

public record MemberInfo(Long memberId, String authority) {

    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(() -> authority);
        return authorities;
    }
}
