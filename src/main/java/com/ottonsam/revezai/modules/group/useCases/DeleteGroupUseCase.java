package com.ottonsam.revezai.modules.group.useCases;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ottonsam.revezai.modules.group.repository.GroupRepository;
import com.ottonsam.revezai.modules.user.repositories.UserRepository;

@Service
public class DeleteGroupUseCase {
    
    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private UserRepository userRepository;

    public void execute(UUID groupId, UUID userId) {
        
        var user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));
        
        var group = groupRepository.findById(groupId)
            .orElseThrow(() -> new RuntimeException("Group not found"));

        if (!group.getOwner().getId().equals(user.getId())) {
            throw new RuntimeException("Only the group owner can delete the group");
        }

        groupRepository.deleteById(groupId);        
    }
}
