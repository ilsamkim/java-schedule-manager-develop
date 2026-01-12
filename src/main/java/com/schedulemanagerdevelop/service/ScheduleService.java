package com.schedulemanagerdevelop.service;

import com.schedulemanagerdevelop.dto.*;
import com.schedulemanagerdevelop.entity.Comment;
import com.schedulemanagerdevelop.entity.Schedule;
import com.schedulemanagerdevelop.entity.User;
import com.schedulemanagerdevelop.repository.CommentRepository;
import com.schedulemanagerdevelop.repository.ScheduleRepository;
import com.schedulemanagerdevelop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    // 저장
    @Transactional
    public CreateScheduleResponse save(CreateScheduleRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));

        Schedule schedule = new Schedule(
                user,
                request.getTitle(),
                request.getContent()
        );
        Schedule savedSchedule = scheduleRepository.save(schedule);

        return new CreateScheduleResponse(
                savedSchedule.getId(),
                savedSchedule.getUser().getId(),
                savedSchedule.getUser().getUsername(),
                savedSchedule.getTitle(),
                savedSchedule.getContent()
        );
    }

    // 단건 조회
    @Transactional(readOnly = true)
    public GetOneScheduleResponse getOne(Long scheduleId) {
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 일정입니다."));

        return new GetOneScheduleResponse(
                schedule.getId(),
                schedule.getUser().getId(),
                schedule.getUser().getUsername(),
                schedule.getTitle(),
                schedule.getContent()
        );
    }

    // 전체 조회
    @Transactional(readOnly = true)
    public List<GetOneScheduleResponse> getAll() {
        List<Schedule> schedules = scheduleRepository.findAll();

        List<GetOneScheduleResponse> dtos = new ArrayList<>();
        for (Schedule schedule : schedules) {
            GetOneScheduleResponse dto = new GetOneScheduleResponse(
                    schedule.getId(),
                    schedule.getUser().getId(),
                    schedule.getUser().getUsername(),
                    schedule.getTitle(),
                    schedule.getContent()
            );
            dtos.add(dto);
        }
        return dtos;
    }

    // 업데이트
    @Transactional
    public UpdateScheduleResponse update(Long scheduleId, UpdateScheduleRequest request) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 일정입니다.")
        );

        schedule.update(
                request.getTitle(),
                request.getContent()
        );

        return new UpdateScheduleResponse(
                schedule.getId(),
                schedule.getUser().getId(),
                schedule.getUser().getUsername(),
                schedule.getTitle(),
                schedule.getContent()
        );
    }

    // 삭제
    @Transactional
    public void delete(Long scheduleId) {
        // 일정이 없는 경우
        if (!scheduleRepository.existsById(scheduleId)) {
            throw new IllegalArgumentException("존재하지 않는 일정입니다.");
        }

        // 일정이 있는 경우
        scheduleRepository.deleteById(scheduleId);
    }

    //페이지네이션 조회
    @Transactional(readOnly = true)
    public Page<GetSchedulePageResponse> getSchedulesWithPaging(int page, int size) {
        // 내림차순 설정
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "updatedAt"));
        Page<Schedule> schedulePage = scheduleRepository.findAll(pageable);

        List<Comment> allComments = commentRepository.findAll();

        // 댓글 개수 계산
        return schedulePage.map(schedule -> {
            int commentCount = 0;
            for (Comment comment : allComments) {
                if (comment.getSchedule().getId().equals(schedule.getId())) {
                    commentCount++;
                }
            }
            return new GetSchedulePageResponse(
                    schedule.getId(),
                    schedule.getTitle(),
                    schedule.getContent(),
                    commentCount,
                    schedule.getCreatedAt(),
                    schedule.getUpdatedAt(),
                    schedule.getUser().getUsername()
            );
        });
    }

}
