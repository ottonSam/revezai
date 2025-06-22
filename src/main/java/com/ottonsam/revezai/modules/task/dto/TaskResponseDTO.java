package com.ottonsam.revezai.modules.task.dto;

import java.time.LocalDate;
import java.util.UUID;

import com.ottonsam.revezai.modules.task.entities.TaskEntity;
import com.ottonsam.revezai.modules.task.entities.TaskStatus;

import lombok.Data;

@Data
public class TaskResponseDTO {
    
    private UUID id;
    private String description;
    private LocalDate date;
    private TaskStatus status;
    private String responsibleName;
    private String groupName;

    public TaskResponseDTO(TaskEntity task) {
        this.id = task.getId();
        this.description = task.getDescription();
        this.date = task.getDate();
        this.status = task.getStatus();
        this.responsibleName = task.getResponsible() != null ? task.getResponsible().getName() : null;
        this.groupName = task.getGroup() != null ? task.getGroup().getName() : null;
    }
}
