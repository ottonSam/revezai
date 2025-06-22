package com.ottonsam.revezai.modules.group.entities;

import java.util.List;
import java.util.UUID;

import com.ottonsam.revezai.modules.user.entites.UserEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity(name = "groups_accounts")
@Data
public class GroupEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank(message = "Group name cannot be empty")
    private String name;

    @NotBlank(message = "Group description cannot be empty")
    private String description;

    @ManyToOne()
    private UserEntity owner;

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
    private List<GroupMemberEntity> members;
}
