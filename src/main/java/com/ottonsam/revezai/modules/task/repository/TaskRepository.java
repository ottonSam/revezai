package com.ottonsam.revezai.modules.task.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ottonsam.revezai.modules.task.entities.TaskEntity;

public interface TaskRepository extends JpaRepository<TaskEntity, UUID> {

    List<TaskEntity> findAllByGroupIdAndDate(UUID groupId, LocalDate date);
    
}
