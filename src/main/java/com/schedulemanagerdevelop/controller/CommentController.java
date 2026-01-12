package com.schedulemanagerdevelop.controller;

import com.schedulemanagerdevelop.dto.CreateCommentRequest;
import com.schedulemanagerdevelop.dto.CreateCommentResponse;
import com.schedulemanagerdevelop.dto.GetCommentResponse;
import com.schedulemanagerdevelop.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    // 댓글 저장
    @PostMapping("/comments")
    public ResponseEntity<CreateCommentResponse> createComment(@Valid @RequestBody CreateCommentRequest requset) {
        CreateCommentResponse result = commentService.save(requset);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    // 전체 댓글 조회
    @GetMapping("/comments")
    public ResponseEntity<List<GetCommentResponse>> getAllComments() {
        List<GetCommentResponse> result = commentService.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    // 특정 일정의 댓글 조회
    @GetMapping("/schedules/{scheduleId}/comments")
    public ResponseEntity<List<GetCommentResponse>> getCommentsBySchedule(@PathVariable Long scheduleId) {
        List<GetCommentResponse> result = commentService.getByScheduleId(scheduleId);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
