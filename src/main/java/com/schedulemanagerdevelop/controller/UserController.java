package com.schedulemanagerdevelop.controller;

import com.schedulemanagerdevelop.dto.*;
import com.schedulemanagerdevelop.repository.UserRepository;
import com.schedulemanagerdevelop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 생성
    @PostMapping ("/users")
    public ResponseEntity<CreateUserResponse> createUser(@RequestBody CreateUserRequest request) {
        CreateUserResponse result = userService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    // 단건 조회
    @GetMapping("/users/{userId}")
    public ResponseEntity<GetOneUserResponse> getOneUser(@PathVariable Long userId) {
        GetOneUserResponse result = userService.getOne(userId);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    // 전체 조회
    @GetMapping("/users")
    public ResponseEntity<List<GetOneUserResponse>> getAllUsers() {
        List<GetOneUserResponse> result = userService.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    // 업데이트
    @PutMapping("/users/{userId}")
    public ResponseEntity<UpdateUserResponse> updateUser(
            @PathVariable Long userId,
            @RequestBody UpdateUserRequest request) {
        UpdateUserResponse result = userService.update(userId, request);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    // 삭제
    @DeleteMapping("/users/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        userService.delete(userId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
