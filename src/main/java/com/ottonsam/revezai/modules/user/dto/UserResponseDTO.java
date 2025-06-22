package com.ottonsam.revezai.modules.user.dto;

import com.ottonsam.revezai.modules.user.entites.UserEntity;

import lombok.Data;

@Data
public class UserResponseDTO {
    
    private String username;
    private String email;
    private String name;

    public UserResponseDTO(UserEntity user) {
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.name = user.getName();
    }
}
