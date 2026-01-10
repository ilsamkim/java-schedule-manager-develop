package com.schedulemanagerdevelop.dto;

import lombok.Getter;

@Getter
public class GetOneUserResponse {

    private final Long id;
    private final String name;
    private final String email;

    public GetOneUserResponse(Long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }
}
