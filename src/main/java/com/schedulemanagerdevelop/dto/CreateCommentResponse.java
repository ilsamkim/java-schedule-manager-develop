package com.schedulemanagerdevelop.dto;

import lombok.Getter;
import java.time.LocalDateTime;

@Getter
public class CreateCommentResponse {

    private final Long id;
    private final Long userId;           // 유저 고유 식별자
    private final String username;       // 유저명
    private final Long scheduleId;       // 일정 고유 식별자
    private final String content;        // 댓글 내용
    private final LocalDateTime createdAt;  // 작성일

    public CreateCommentResponse(Long id, Long userId, String username, Long scheduleId, String content, LocalDateTime createdAt) {
        this.id = id;
        this.userId = userId;
        this.username = username;
        this.scheduleId = scheduleId;
        this.content = content;
        this.createdAt = createdAt;
    }
}