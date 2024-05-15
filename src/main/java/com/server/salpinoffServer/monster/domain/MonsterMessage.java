package com.server.salpinoffServer.monster.domain;

import com.server.salpinoffServer.infra.entity.BaseEntity;
import com.server.salpinoffServer.monster.service.dto.EncouragementMessageRequest;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Entity
@Getter
@Table(name = "monster_message")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MonsterMessage extends BaseEntity {

    @Column(nullable = false)
    private Long monsterId;

    @Column(nullable = false)
    private String sender;

    @Column(nullable = false, length = 500)
    private String content;

    @Column(nullable = false)
    private boolean checked;

    private MonsterMessage(Long monsterId, String sender, String content) {
        this.monsterId = monsterId;
        this.sender = sender;
        this.content = content;
        this.checked = false;
    }

    public static MonsterMessage from(Long monsterId, EncouragementMessageRequest request) {
        return new MonsterMessage(monsterId, request.getSender(), request.getContent());
    }

    public boolean isOwner(Long monsterId) {
        return Objects.equals(monsterId, this.monsterId);
    }

    public void check() {
        this.checked = true;
    }
}
