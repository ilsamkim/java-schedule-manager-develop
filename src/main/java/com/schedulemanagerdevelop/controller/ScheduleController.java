package com.schedulemanagerdevelop.controller;

import com.schedulemanagerdevelop.dto.*;
import com.schedulemanagerdevelop.service.ScheduleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    // 저장
    @PostMapping("/schedules")
    public ResponseEntity<CreateScheduleResponse> createSchedule(@Valid @RequestBody CreateScheduleRequest request) {
        CreateScheduleResponse result = scheduleService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    // 단 건 조회
    @GetMapping("/schedules/{scheduleId}")
    public ResponseEntity<GetOneScheduleResponse> getOneSchedule(@PathVariable Long scheduleId) {
        GetOneScheduleResponse result = scheduleService.getOne(scheduleId);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    // 전체 조회
    @GetMapping("/schedules")
    public ResponseEntity<List<GetOneScheduleResponse>> getAllSchedules() {
        List<GetOneScheduleResponse> result = scheduleService.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    // 업데이트
    @PutMapping("/schedules/{scheduleId}")
    public ResponseEntity<UpdateScheduleResponse> updateSchedules(
            @PathVariable Long scheduleId,
            @Valid @RequestBody UpdateScheduleRequest request) {
        UpdateScheduleResponse result = scheduleService.update(scheduleId, request);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    // 삭제
    @DeleteMapping("/schedules/{scheduleId}")
    public ResponseEntity<Void> deleteSchedules(@PathVariable Long scheduleId) {
        scheduleService.delete(scheduleId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // 페이지네이션 조회
    @GetMapping("/schedules/page")
    public ResponseEntity<Page<GetSchedulePageResponse>> getSchedulesWithPaging(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<GetSchedulePageResponse> result = scheduleService.getSchedulesWithPaging(page, size);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
