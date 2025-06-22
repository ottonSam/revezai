package com.ottonsam.revezai.modules.group.useCases;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ottonsam.revezai.modules.group.dto.GroupResponseDTO;
import com.ottonsam.revezai.modules.group.entities.GroupEntity;
import com.ottonsam.revezai.modules.group.entities.GroupMemberEntity;
import com.ottonsam.revezai.modules.group.entities.GroupMemberStatus;
import com.ottonsam.revezai.modules.group.repository.GroupRepository;
import com.ottonsam.revezai.modules.user.dto.UserResponseDTO;
import com.ottonsam.revezai.modules.user.repositories.UserRepository;

@Service
public class CreateGroupUseCase {
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GroupRepository groupRepository;

    public GroupResponseDTO execute(GroupEntity group, UUID userId) {
        
        var user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));
        group.setOwner(user);

        if (group.getMembers() == null) {
            group.setMembers(List.of());
        }

        var ownerMember = new GroupMemberEntity();
        ownerMember.setUser(user);
        ownerMember.setGroup(group);
        ownerMember.setStatus(GroupMemberStatus.ACCEPTED);
        group.setMembers(List.of(ownerMember));

        List<UserResponseDTO> members = group.getMembers().stream()
            .filter(member -> member.getStatus() == GroupMemberStatus.ACCEPTED)
            .map(member -> member.getUser())
            .map(UserResponseDTO::new)
            .toList();
         
        return new GroupResponseDTO(groupRepository.save(group), members);
    }
}
