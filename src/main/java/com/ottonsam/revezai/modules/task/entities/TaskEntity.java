package com.ottonsam.revezai.modules.task.entities;

import java.time.LocalDate;
import java.util.UUID;

import com.ottonsam.revezai.modules.group.entities.GroupEntity;
import com.ottonsam.revezai.modules.user.entites.UserEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity(name = "tasks")
@Data
public class TaskEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank(message = "Task description cannot be empty")
    private String description;

    @NotNull(message = "Task date cannot be null")
    private LocalDate date;

    @Enumerated(EnumType.ORDINAL)
    private TaskStatus status;

    @ManyToOne()
    private UserEntity responsible;

    @ManyToOne()
    private GroupEntity group;
}
