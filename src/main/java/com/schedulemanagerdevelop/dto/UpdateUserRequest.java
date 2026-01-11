package com.schedulemanagerdevelop.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UpdateUserRequest {

    @Size(min = 2, max = 4, message = "유저명은 2~4글자여야 합니다.")
    private String username;

    @Email(message = "올바른 이메일 형식이 아닙니다.")
    private String email;
}
