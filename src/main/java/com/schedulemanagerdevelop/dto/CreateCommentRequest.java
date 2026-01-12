package com.schedulemanagerdevelop.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CreateCommentRequest {

    @NotNull(message = "유저 ID는 필수입니다.")
    private Long userId;

    @NotNull(message = "일정 ID는 필수입니다.")
    private Long scheduleId;

    @NotBlank(message = "댓글 내용은 필수입니다.")
    private String content;
}
