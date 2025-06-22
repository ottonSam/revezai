package com.ottonsam.revezai.modules.group.useCases;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ottonsam.revezai.modules.group.dto.GroupResponseDTO;
import com.ottonsam.revezai.modules.group.entities.GroupMemberStatus;
import com.ottonsam.revezai.modules.group.repository.GroupRepository;
import com.ottonsam.revezai.modules.user.dto.UserResponseDTO;

@Service
public class DetailGroupUseCase {
    
    @Autowired
    private GroupRepository groupRepository;

    public GroupResponseDTO execute(UUID groupId, UUID userId) {
        
        var group = groupRepository.findById(groupId)
            .orElseThrow(() -> new RuntimeException("Group not found"));

        var members = group.getMembers();
        boolean isMember = members.stream().anyMatch(member -> member.getUser().getId().equals(userId));
        if (!isMember) {
            throw new RuntimeException("User is not a member of the group");
        }

        if (group.getMembers() == null) {
            group.setMembers(List.of());
        }
        List<UserResponseDTO> membersFormatted = group.getMembers().stream()
            .filter(member -> member.getStatus() == GroupMemberStatus.ACCEPTED)
            .map(member -> member.getUser())
            .map(UserResponseDTO::new)
            .toList();

        return new GroupResponseDTO(group, membersFormatted);
    }
}
