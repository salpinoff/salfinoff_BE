package com.server.salpinoffServer.infra.ui.dto;

import jakarta.validation.constraints.Min;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Setter
public abstract class AbstractPageRequest {

    @Min(1)
    private int page = 1;
    @Min(1)
    private int size = 10;

    public Pageable getPageable() {
        return PageRequest.of(page - 1, size);
    }
}
