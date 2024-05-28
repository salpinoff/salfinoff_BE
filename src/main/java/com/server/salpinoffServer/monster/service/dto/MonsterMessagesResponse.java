package com.server.salpinoffServer.monster.service.dto;

import com.server.salpinoffServer.monster.domain.MonsterMessage;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MonsterMessagesResponse {
    private Long messageId;
    private String sender;
    private String content;
    private boolean checked;

    public MonsterMessagesResponse() {
    }

    @Builder
    public MonsterMessagesResponse(Long messageId, String sender, String content, boolean checked) {
        this.messageId = messageId;
        this.sender = sender;
        this.content = content;
        this.checked = checked;
    }

    public static MonsterMessagesResponse of(MonsterMessage monsterMessage) {
        return MonsterMessagesResponse.builder()
                .messageId(monsterMessage.getId())
                .sender(monsterMessage.getSender())
                .content(monsterMessage.getContent())
                .checked(monsterMessage.isChecked())
                .build();
    }
}
