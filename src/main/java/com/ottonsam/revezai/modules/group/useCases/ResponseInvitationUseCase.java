package com.ottonsam.revezai.modules.group.useCases;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ottonsam.revezai.modules.group.dto.ResponseToInvitationRequestDTO;
import com.ottonsam.revezai.modules.group.entities.GroupMemberStatus;
import com.ottonsam.revezai.modules.group.repository.GroupMemberRepository;

@Service
public class ResponseInvitationUseCase {
    
    @Autowired
    private GroupMemberRepository groupMemberRepository;

    public void execute(ResponseToInvitationRequestDTO responseToInvitationRequestDTO, UUID userId) {
    
        var groupMember = groupMemberRepository.findById(UUID.fromString(responseToInvitationRequestDTO.getInviteId()))
            .orElseThrow(() -> new RuntimeException("Group member not found"));
    
        if (!groupMember.getUser().getId().equals(userId)) {
            throw new RuntimeException("User is not the one invited to this group");
        }

        if (!groupMember.getStatus().equals(GroupMemberStatus.PENDING)) {
            throw new RuntimeException("Invitation is not pending");
        }

        if (responseToInvitationRequestDTO.getResponse().equals("ACCEPTED")) {
            groupMember.setStatus(GroupMemberStatus.ACCEPTED);
        } else if (responseToInvitationRequestDTO.getResponse().equals("REJECTED")) {
            groupMember.setStatus(GroupMemberStatus.REJECTED);
        } else {
            throw new RuntimeException("Invalid response");
        }

        groupMemberRepository.save(groupMember);
    }
}

