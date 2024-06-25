package com.server.salpinoffServer.monster.service.dto;

import com.server.salpinoffServer.infra.ui.dto.PageResponse;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

@Getter
public class MonsterMessagesPageResponse extends PageResponse<MonsterMessagesResponse> {

    private final long checkedMessageCount;
    private final long uncheckedMessageCount;

    @Builder
    public MonsterMessagesPageResponse(Page<MonsterMessagesResponse> page,
                                       long checkedMessageCount,
                                       long uncheckedMessageCount) {
        super(page);
        this.checkedMessageCount = checkedMessageCount;
        this.uncheckedMessageCount = uncheckedMessageCount;
    }
}
