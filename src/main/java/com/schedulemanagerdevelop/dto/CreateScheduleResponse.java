package com.schedulemanagerdevelop.dto;

import lombok.Getter;

@Getter
public class CreateScheduleResponse {

    private final Long id;
    private final Long userId;
    private final String username;
    private final String title;
    private final String content;

    public CreateScheduleResponse(Long id, Long userId, String username, String title, String content) {
        this.id = id;
        this.userId = userId;
        this.username = username;
        this.title = title;
        this.content = content;
    }
}
