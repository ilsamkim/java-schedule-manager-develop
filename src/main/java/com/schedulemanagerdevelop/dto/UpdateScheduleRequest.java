package com.schedulemanagerdevelop.dto;

import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UpdateScheduleRequest {

    @Size(max = 10, message = "일정 제목은 10글자 이내여야 합니다.")
    private String title;

    @Size(max = 200, message = "일정 내용은 200글자 이내여야 합니다.")
    private String content;

}
