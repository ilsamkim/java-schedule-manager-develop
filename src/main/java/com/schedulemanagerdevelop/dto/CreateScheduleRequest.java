package com.schedulemanagerdevelop.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class CreateScheduleRequest {

    @NotNull(message = "유저 ID는 필수입니다.")
    private Long userId;

    @NotBlank(message = "일정 제목은 필수입니다.")
    @Size(max = 10, message = "일정 제목은 10글자 이내여야 합니다.")
    private String title;

    @NotBlank(message = "일정 내용은 필수입니다.")
    @Size(max = 200, message = "일정 내용은 200글자 이내여야 합니다.")
    private String content;
}
