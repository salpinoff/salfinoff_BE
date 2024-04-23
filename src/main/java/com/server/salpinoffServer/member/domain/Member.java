package com.server.salpinoffServer.member.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String socialKey;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Authority authority;

    private Member(String socialKey, Authority authority) {
        this.socialKey = socialKey;
        this.authority = authority;
    }

    public static Member toUser(String socialKey) {
        return new Member(socialKey, Authority.USER);
    }

    public enum Authority {
        USER
    }
}
