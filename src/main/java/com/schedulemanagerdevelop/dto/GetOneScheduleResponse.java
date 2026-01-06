package com.schedulemanagerdevelop.dto;

import lombok.Getter;

@Getter
public class GetOneScheduleResponse {

    private final Long id;
    private final String name;
    private final String title;
    private final String content;

    public GetOneScheduleResponse(Long id, String name, String title, String content) {
        this.id = id;
        this.name = name;
        this.title = title;
        this.content = content;
    }
}
