package com.server.salpinoffServer.monster.service.dto;

public class MonsterMessagesResponse {
    private Long messageId;
    private String sender;
    private String content;
    private boolean checked;

    public MonsterMessagesResponse() {
    }

    public MonsterMessagesResponse(Long messageId, String sender, String content, boolean checked) {
        this.messageId = messageId;
        this.sender = sender;
        this.content = content;
        this.checked = checked;
    }
}
