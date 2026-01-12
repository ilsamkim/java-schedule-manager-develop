package com.schedulemanagerdevelop.service;

import com.schedulemanagerdevelop.dto.CreateCommentRequest;
import com.schedulemanagerdevelop.dto.CreateCommentResponse;
import com.schedulemanagerdevelop.dto.GetCommentResponse;
import com.schedulemanagerdevelop.entity.Comment;
import com.schedulemanagerdevelop.entity.Schedule;
import com.schedulemanagerdevelop.entity.User;
import com.schedulemanagerdevelop.repository.CommentRepository;
import com.schedulemanagerdevelop.repository.ScheduleRepository;
import com.schedulemanagerdevelop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final ScheduleRepository scheduleRepository;

    // 댓글 저장
    @Transactional
    public CreateCommentResponse save(CreateCommentRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));

        Schedule schedule = scheduleRepository.findById(request.getScheduleId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 일정입니다."));

        Comment comment = new Comment(
                user,
                schedule,
                request.getContent()
        );

        Comment savedComment = commentRepository.save(comment);

        return new CreateCommentResponse(
                savedComment.getId(),
                savedComment.getUser().getId(),
                savedComment.getUser().getUsername(),
                savedComment.getSchedule().getId(),
                savedComment.getContent(),
                savedComment.getCreatedAt()
        );
    }

    // 전체 댓글 조회
    @Transactional(readOnly = true)
    public List<GetCommentResponse> getAll(){
        List<Comment> comments = commentRepository.findAll();
        List<GetCommentResponse> dtos = new ArrayList<>();

        for(Comment comment : comments){
            GetCommentResponse dto = new GetCommentResponse(
                    comment.getId(),
                    comment.getUser().getId(),
                    comment.getUser().getUsername(),
                    comment.getSchedule().getId(),
                    comment.getContent(),
                    comment.getCreatedAt(),
                    comment.getUpdatedAt()
            );
            dtos.add(dto);
        }
        return dtos;
    }

    // 특정 일정의 댓글 조회
    @Transactional(readOnly = true)
    public List<GetCommentResponse> getByScheduleId(Long scheduleId){
        if(!scheduleRepository.existsById(scheduleId)){
            throw new IllegalArgumentException("존재하지 않는 일정입니다.");
        }

        List<Comment> comments = commentRepository.findAll();
        List<GetCommentResponse> dtos = new ArrayList<>();

        for (Comment comment : comments){
            if (comment.getSchedule().getId().equals(scheduleId)){
                GetCommentResponse dto = new GetCommentResponse(
                        comment.getId(),
                        comment.getUser().getId(),
                        comment.getUser().getUsername(),
                        comment.getSchedule().getId(),
                        comment.getContent(),
                        comment.getCreatedAt(),
                        comment.getUpdatedAt()
                );
                dtos.add(dto);
            }
        }
        return dtos;
    }
}
