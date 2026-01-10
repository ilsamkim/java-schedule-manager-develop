package com.schedulemanagerdevelop.service;

import com.schedulemanagerdevelop.dto.*;
import com.schedulemanagerdevelop.entity.Schedule;
import com.schedulemanagerdevelop.entity.User;
import com.schedulemanagerdevelop.repository.UserRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    // 저장
    @Transactional
    public CreateUserResponse save(CreateUserRequest request) {
        // 비밀번호 길이 검증
        validatePassword(request.getPassword());

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }

        User user = new User(
                request.getUsername(),
                request.getEmail(),
                request.getPassword()
        );

        User savedUser = userRepository.save(user);
        return new CreateUserResponse(
                savedUser.getId(),
                savedUser.getUsername(),
                savedUser.getEmail()
        );
    }

    // 비밀번호 유효성 검사
    private void validatePassword(String password) {
        if (password == null || password.length() < 8) {
            throw new IllegalArgumentException("비밀번호는 8글자 이상이어야 합니다.");
        }
    }

    // 단건 조회
    @Transactional(readOnly = true)
    public GetOneUserResponse getOne(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));
        return new GetOneUserResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail()
        );
    }

    // 전체 조회
    @Transactional(readOnly = true)
    public List<GetOneUserResponse> getAll() {
        List<User> users = userRepository.findAll();
        List<GetOneUserResponse> dtos = new ArrayList<>();
        for (User user : users) {
            GetOneUserResponse dto = new GetOneUserResponse(
                    user.getId(),
                    user.getUsername(),
                    user.getEmail()
            );
            dtos.add(dto);
        }
        return dtos;
    }

    // 업데이트
    @Transactional
    public UpdateUserResponse update(Long userId, UpdateUserRequest request) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));

        if (request.getEmail() != null &&
                !user.getEmail().equals(request.getEmail()) &&
                userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }

        user.update(request.getUsername(), request.getEmail());

        return new UpdateUserResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail()
        );
    }

    // 삭제
    @Transactional
    public void delete(Long userId) {
        // 유저가 없는 경우
        if (!userRepository.existsById(userId)) {
            throw new IllegalArgumentException("존재하지 않는 유저입니다.");
        }

        // 유저가 있는 경우
        userRepository.deleteById(userId);
    }
}
