package com.ottonsam.revezai.modules.group.dto;

import java.util.List;

import com.ottonsam.revezai.modules.group.entities.GroupEntity;
import com.ottonsam.revezai.modules.user.dto.UserResponseDTO;

import lombok.Data;

@Data
public class GroupResponseDTO {
    
    private String id;
    private String name;
    private String description;
    private UserResponseDTO owner;
    private List<UserResponseDTO> members;

    public GroupResponseDTO(GroupEntity group, List<UserResponseDTO> members) {
        this.id = group.getId().toString();
        this.name = group.getName();
        this.description = group.getDescription();
        this.owner = new UserResponseDTO(group.getOwner());
        this.members = members;
    }
}
