package com.server.salpinoffServer.infra.ui.dto;

import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
public class PageResponse<T> {

    private final List<T> content;
    private final int size;
    private final long page;
    private final long totalElements;

    public PageResponse(Page<T> page) {
        this.content = page.getContent();
        this.size = page.getSize();
        this.page = page.getNumber() + 1;
        this.totalElements = page.getTotalElements();
    }

}
