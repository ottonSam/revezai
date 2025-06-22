package com.ottonsam.revezai.modules.group.dto;

import java.util.UUID;

import com.ottonsam.revezai.modules.group.entities.GroupMemberEntity;
import com.ottonsam.revezai.modules.group.entities.GroupMemberStatus;

import lombok.Data;

@Data
public class GroupMemberResponseDTO {
    private UUID id;
    private String groupName;
    private String groupOwnerName;
    private GroupMemberStatus status;

    public GroupMemberResponseDTO(GroupMemberEntity groupMember) {
        this.id = groupMember.getId();
        this.groupName = groupMember.getGroup().getName();
        this.groupOwnerName = groupMember.getGroup().getOwner().getUsername();
        this.status = groupMember.getStatus();
    }
}
