package com.schedulemanagerdevelop.dto;

import lombok.Getter;

@Getter
public class UpdateScheduleResponse {

    private final Long id;
    private final Long userId;
    private final String name;
    private final String title;
    private final String content;

    public UpdateScheduleResponse(Long id, Long userId, String name, String title, String content) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.title = title;
        this.content = content;
    }
}
