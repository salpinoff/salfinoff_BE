package com.server.salpinoffServer.member.domain;

import com.server.salpinoffServer.member.service.dto.MemberInfoRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

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

    @Column(nullable = false, length = 20)
    private String username;

    private Member(String socialKey, Authority authority) {
        this.socialKey = socialKey;
        this.authority = authority;
        this.username = "";
    }

    public static Member toUser(String socialKey) {
        return new Member(socialKey, Authority.USER);
    }

    public void update(MemberInfoRequest request) {
        this.username = request.getUsername();
    }

    public enum Authority {
        USER
    }

    public enum SignUpStatus {
        REGISTRATION_ONLY(100),
        NAME_PROVIDED(101),
        PROCESS_COMPLETED(102);

        private final int value;

        SignUpStatus(int value) {
            this.value = value;
        }

        public int value() {
            return this.value;
        }
    }

    public SignUpStatus signUpStatus(boolean hasMonster) {
        if (!StringUtils.hasText(this.username)) {
            return SignUpStatus.REGISTRATION_ONLY;
        }
        if (!hasMonster) {
            return SignUpStatus.NAME_PROVIDED;
        }
        return SignUpStatus.PROCESS_COMPLETED;
    }
}
