package com.ottonsam.revezai.modules.group.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ResponseToInvitationRequestDTO {
    
    @NotBlank(message = "Invite ID cannot be empty")
    private String inviteId;

    @NotBlank(message = "Response cannot be empty")
    private String response;
}
