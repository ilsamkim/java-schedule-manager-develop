package com.schedulemanagerdevelop.dto;

import lombok.Getter;

@Getter
public class RegisterResponse {

    private final Long id;

    public RegisterResponse(Long id){
        this.id = id;
    }
}
