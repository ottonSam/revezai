package com.ottonsam.revezai.modules.task.dto;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RecurringTasksRequestDTO {

    @NotBlank(message = "Task description cannot be empty")
    private String description;

    @NotNull(message = "groupId cannot be null")
    private UUID groupId;

    @NotNull(message = "daysOfWeek cannot be null")
    private List<DayOfWeek> daysOfWeek;

    @NotNull(message = "endDate cannot be null")
    @AssertTrue(message = "End date cannot be more than 1000 days from today")
    public boolean isEndDateWithinLimit() {
        if (endDate == null) return true;
        return !endDate.isAfter(LocalDate.now().plusDays(1000));
    }
    @AssertTrue(message = "End date cannot be before today")
    public boolean isEndDateNotBeforeToday() {
        if (endDate == null) return true;
        return !endDate.isBefore(LocalDate.now());
    }
    private LocalDate endDate;

    @NotNull(message = "Responsible IDs cannot be null")
    private List<UUID> responsibleIds;
}
