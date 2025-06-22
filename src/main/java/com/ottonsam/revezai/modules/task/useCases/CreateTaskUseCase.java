package com.ottonsam.revezai.modules.task.useCases;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ottonsam.revezai.modules.group.repository.GroupRepository;
import com.ottonsam.revezai.modules.task.dto.RecurringTasksRequestDTO;
import com.ottonsam.revezai.modules.task.dto.TaskResponseDTO;
import com.ottonsam.revezai.modules.task.entities.TaskEntity;
import com.ottonsam.revezai.modules.task.entities.TaskStatus;
import com.ottonsam.revezai.modules.task.repository.TaskRepository;
import com.ottonsam.revezai.modules.user.repositories.UserRepository;

@Service
public class CreateTaskUseCase {
    
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private UserRepository userRepository;

    public TaskResponseDTO execute(TaskEntity task, UUID groupId, UUID responsibleId) {
        
        var group = groupRepository.findById(groupId)
            .orElseThrow(() -> new IllegalArgumentException("Group not found"));

        var responsible = userRepository.findById(responsibleId)
            .orElseThrow(() -> new IllegalArgumentException("Responsible user not found"));
        
        boolean isMember = group.getMembers().stream()
            .anyMatch(member -> member.getUser().getId().equals(responsibleId));

        if (!isMember) {
            throw new IllegalArgumentException("Responsible is not a member of the group");
        }

        task.setGroup(group);
        task.setResponsible(responsible);
        task.setStatus(TaskStatus.TODO);
        TaskEntity savedTask = taskRepository.save(task);
        return new TaskResponseDTO(savedTask);
    }

    public List<TaskResponseDTO> createRecurringTasks(
        RecurringTasksRequestDTO request,
        UUID userId
    ) {

        List<TaskResponseDTO> createdTasks = new ArrayList<>();
        
        if (request.getResponsibleIds() == null || request.getResponsibleIds().isEmpty()) {
            throw new IllegalArgumentException("Responsible IDs list cannot be empty");
        }

        var group = groupRepository.findById(request.getGroupId())
            .orElseThrow(() -> new IllegalArgumentException("Group not found"));

        boolean isUserMember = group.getMembers().stream()
            .anyMatch(member -> member.getUser().getId().equals(userId));

        if (!isUserMember) {
            throw new IllegalArgumentException("User is not a member of the group");
        }

        Queue<UUID> responsibleQueue = new LinkedList<>(request.getResponsibleIds());
        LocalDate currentDate = LocalDate.now();

        while (!currentDate.isAfter(request.getEndDate())) {
            if (request.getDaysOfWeek().contains(currentDate.getDayOfWeek())) {
                UUID responsibleId = responsibleQueue.poll();
                if (responsibleId == null) {
                    responsibleId = request.getResponsibleIds().get(0);
                }
                responsibleQueue.offer(responsibleId);

                TaskEntity task = new TaskEntity();
                task.setDescription(request.getDescription());
                task.setDate(currentDate);
                TaskResponseDTO response = execute(task, request.getGroupId(), responsibleId);
                createdTasks.add(response);
            }
            currentDate = currentDate.plusDays(1);
        }
        return createdTasks;
    }
}
