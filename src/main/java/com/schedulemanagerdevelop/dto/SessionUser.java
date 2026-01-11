package com.schedulemanagerdevelop.dto;

import com.schedulemanagerdevelop.entity.User;
import lombok.Getter;

@Getter
public class SessionUser {

    private final Long id;
    private final String username;
    private final String email;

    public SessionUser(User user){
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
    }
}
