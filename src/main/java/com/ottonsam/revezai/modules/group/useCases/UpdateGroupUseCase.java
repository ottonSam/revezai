package com.ottonsam.revezai.modules.group.useCases;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ottonsam.revezai.modules.group.dto.GroupResponseDTO;
import com.ottonsam.revezai.modules.group.entities.GroupEntity;
import com.ottonsam.revezai.modules.group.entities.GroupMemberStatus;
import com.ottonsam.revezai.modules.group.repository.GroupRepository;
import com.ottonsam.revezai.modules.user.dto.UserResponseDTO;
import com.ottonsam.revezai.modules.user.repositories.UserRepository;

@Service
public class UpdateGroupUseCase {
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GroupRepository groupRepository;

    public GroupResponseDTO execute(GroupEntity group,UUID groupID , UUID userId) {
        
        var user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));
        group.setOwner(user);

        var originalGroup = groupRepository.findById(groupID)
            .orElseThrow(() -> new RuntimeException("Group not found"));

        if (!originalGroup.getOwner().getId().equals(user.getId())) {
            throw new RuntimeException("Only the group owner can update the group");
        }

        originalGroup.setName(group.getName());
        originalGroup.setDescription(group.getDescription());

        if (originalGroup.getMembers() == null) {
            originalGroup.setMembers(List.of());
        }

        List<UserResponseDTO> members = originalGroup.getMembers().stream()
            .filter(member -> member.getStatus() == GroupMemberStatus.ACCEPTED)
            .map(member -> member.getUser())
            .map(UserResponseDTO::new)
            .toList();
         
        return new GroupResponseDTO(groupRepository.save(originalGroup), members);
    }
}
