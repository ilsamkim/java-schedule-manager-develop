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

    // 회원가입
    @Transactional
    public User register(RegisterRequest request) {
        if(userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }

        User user = new User(
                request.getUsername(),
                request.getEmail(),
                request.getPassword()
        );

        return userRepository.save(user);
    }

    // 로그인
    @Transactional(readOnly = true)
    public User login(LoginRequest request){
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("이메일 또는 비밀번호가 일치하지 않습니다."));

        if (!user.getPassword().equals(request.getPassword())) {
            throw new IllegalArgumentException("이메일 또는 비밀번호가 일치하지 않습니다.");
        }

        return user;
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
