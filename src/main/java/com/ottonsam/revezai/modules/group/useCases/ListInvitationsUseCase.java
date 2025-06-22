package com.ottonsam.revezai.modules.group.useCases;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ottonsam.revezai.modules.group.dto.GroupMemberResponseDTO;
import com.ottonsam.revezai.modules.group.entities.GroupMemberStatus;
import com.ottonsam.revezai.modules.group.repository.GroupMemberRepository;

@Service
public class ListInvitationsUseCase {
    
    @Autowired
    private GroupMemberRepository groupMemberRepository;

    public List<GroupMemberResponseDTO> execute(UUID userId) {
        
        var groupMembers = groupMemberRepository.findAllByUserIdAndStatus(userId, GroupMemberStatus.PENDING);

        if (groupMembers.isEmpty()) {
            return List.of();
        }

        return groupMembers.stream()
            .map(member -> new GroupMemberResponseDTO(
                member
            ))
            .toList();
    }

}
