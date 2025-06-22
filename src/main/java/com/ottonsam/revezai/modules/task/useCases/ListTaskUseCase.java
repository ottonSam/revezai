package com.ottonsam.revezai.modules.task.useCases;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ottonsam.revezai.modules.group.repository.GroupRepository;
import com.ottonsam.revezai.modules.task.dto.TaskResponseDTO;
import com.ottonsam.revezai.modules.task.entities.TaskStatus;
import com.ottonsam.revezai.modules.task.repository.TaskRepository;

@Service
public class ListTaskUseCase {
    
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private GroupRepository groupRepository;

    public List<TaskResponseDTO> execute(UUID groupId, LocalDate date, UUID responsibleId) {
        var group = groupRepository.findById(groupId)
            .orElseThrow(() -> new IllegalArgumentException("Group not found"));
        
        if (responsibleId != null && group.getMembers().stream().noneMatch(member -> member.getUser().getId().equals(responsibleId))) {
            throw new IllegalArgumentException("Responsible is not a member of the group");
        }

        var today = LocalDate.now();
        var tasks = taskRepository.findAllByGroupIdAndDate(group.getId(), date);

        tasks.forEach(task -> {
            if (date.isBefore(today) && TaskStatus.TODO.equals(task.getStatus())) {
                task.setStatus(TaskStatus.OVERDUE);
                taskRepository.save(task);
            }
        });

        return taskRepository.findAllByGroupIdAndDate(group.getId(), date)
            .stream()
            .map(TaskResponseDTO::new)
            .toList();
    }
}
