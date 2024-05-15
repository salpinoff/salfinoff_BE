package com.server.salpinoffServer.monster.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MonsterMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long monsterId;

    @Column(nullable = false)
    private String sender;

    @Column(nullable = false, length = 500)
    private String content;

    @Column(nullable = false)
    private boolean checked;

    public MonsterMessage(Long monsterId, String sender, String content) {
        this.monsterId = monsterId;
        this.sender = sender;
        this.content = content;
        this.checked = false;
    }

    public boolean isOwner(Long monsterId) {
        return Objects.equals(monsterId, this.monsterId);
    }

    public void check() {
        this.checked = true;
    }
}
