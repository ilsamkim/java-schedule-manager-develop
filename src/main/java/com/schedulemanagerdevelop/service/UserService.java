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
        User user = new User(
                request.getUsername(),
                request.getEmail()
        );
        User savedUser = userRepository.save(user);
        return new CreateUserResponse(
                savedUser.getId(),
                savedUser.getUsername(),
                savedUser.getEmail()
        );
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
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));
        user.update(
                request.getUsername(),
                request.getEmail()
        );

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
