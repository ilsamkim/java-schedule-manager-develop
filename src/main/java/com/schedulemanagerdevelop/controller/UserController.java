package com.schedulemanagerdevelop.controller;

import com.schedulemanagerdevelop.dto.*;
import com.schedulemanagerdevelop.entity.User;
import com.schedulemanagerdevelop.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 회원가입
    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@Valid @RequestBody RegisterRequest request) {
        User user = userService.register(request);
        RegisterResponse response = new RegisterResponse(user.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request, HttpSession session) {
        User user = userService.login(request);
        SessionUser sessionUser = new SessionUser(user);
        session.setAttribute("loginUser", sessionUser);
        LoginResponse response = new LoginResponse(user.getId(), user.getEmail());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // 로그아웃
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@SessionAttribute(name = "loginUser", required = false) SessionUser sessionUser, HttpSession session) {
        if (sessionUser == null) {
            return ResponseEntity.badRequest().build();
        }
        session.invalidate();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
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
            @Valid @RequestBody UpdateUserRequest request) {
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
