package com.ottonsam.revezai.modules.task.controllers;

import java.time.LocalDate;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ottonsam.revezai.modules.task.dto.RecurringTasksRequestDTO;
import com.ottonsam.revezai.modules.task.entities.TaskEntity;
import com.ottonsam.revezai.modules.task.useCases.CompleteTaskUseCase;
import com.ottonsam.revezai.modules.task.useCases.CreateTaskUseCase;
import com.ottonsam.revezai.modules.task.useCases.ListTaskUseCase;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/tasks")
public class TaskController {
    
    @Autowired
    private CreateTaskUseCase createTaskUseCase;

    @Autowired
    private ListTaskUseCase listTaskUseCase;

    @Autowired
    private CompleteTaskUseCase completeTaskUseCase;

    @PostMapping("/{groupId}")
    public ResponseEntity<Object> createTask(@Valid @RequestBody TaskEntity entity, @PathVariable String groupId, HttpServletRequest request) {
        try {
            String userIdString = (String) request.getAttribute("user_id");
            return ResponseEntity.status(HttpStatus.CREATED)
                .body(createTaskUseCase.execute(entity, UUID.fromString(groupId),userIdString != null ? UUID.fromString(userIdString) : null));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @PostMapping("/recurring")
    public ResponseEntity<Object> createRecurringTask(@Valid @RequestBody RecurringTasksRequestDTO entity, HttpServletRequest request) {
        try {
            String userIdString = (String) request.getAttribute("user_id");
            return ResponseEntity.status(HttpStatus.CREATED)
                .body(createTaskUseCase.createRecurringTasks(entity, userIdString != null ? UUID.fromString(userIdString) : null));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{groupId}/{date}")
    public ResponseEntity<Object> listTasks(@PathVariable String groupId, @PathVariable String date, HttpServletRequest request) {
        try {
            String userIdString = (String) request.getAttribute("user_id");
            LocalDate localDate = java.time.LocalDate.parse(date);
            return ResponseEntity.ok()
                .body(listTaskUseCase.execute(UUID.fromString(groupId), localDate, userIdString != null ? UUID.fromString(userIdString) : null));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/{groupId}/{taskId}/complete")
    public ResponseEntity<Object> completeTask(@PathVariable String taskId, @PathVariable String groupId,  HttpServletRequest request) {
        try {
            String userIdString = (String) request.getAttribute("user_id");
            completeTaskUseCase.execute(UUID.fromString(taskId), UUID.fromString(groupId), userIdString != null ? UUID.fromString(userIdString) : null);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
