package com.schedulemanagerdevelop.dto;

import lombok.Getter;

@Getter
public class GetOneUserResponse {

    private final Long id;
    private final String username;
    private final String email;

    public GetOneUserResponse(Long id, String username, String email) {
        this.id = id;
        this.username = username;
        this.email = email;
    }
}
