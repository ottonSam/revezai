package com.ottonsam.revezai.modules.group.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class InviteRequestDTO {
    @NotBlank(message = "Username cannot be empty")
    private String username;

    @NotBlank(message = "Group ID cannot be empty")
    @org.hibernate.validator.constraints.UUID(message = "Group ID must be a valid UUID")
    private String groupId;
}
