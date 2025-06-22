package com.ottonsam.revezai.modules.task.useCases;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ottonsam.revezai.modules.group.repository.GroupRepository;
import com.ottonsam.revezai.modules.task.entities.TaskStatus;
import com.ottonsam.revezai.modules.task.repository.TaskRepository;

@Service
public class CompleteTaskUseCase {
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private GroupRepository groupRepository;

    public void execute(UUID taskId, UUID groupId, UUID responsibleId) {
        var group = groupRepository.findById(groupId)
            .orElseThrow(() -> new IllegalArgumentException("Group not found"));

        if (group.getMembers().stream().noneMatch(member -> member.getUser().getId().equals(responsibleId))) {
            throw new IllegalArgumentException("Responsible is not a member of the group");
        }
        
        var task = taskRepository.findById(taskId)
            .orElseThrow(() -> new IllegalArgumentException("Task not found"));

        if (!task.getGroup().getId().equals(group.getId())) {
            throw new IllegalArgumentException("Task does not belong to the specified group");
        }

        task.setStatus(TaskStatus.COMPLETED);
        taskRepository.save(task);
    }
}
