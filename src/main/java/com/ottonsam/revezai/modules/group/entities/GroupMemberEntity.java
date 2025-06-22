package com.ottonsam.revezai.modules.group.entities;

import java.util.UUID;

import com.ottonsam.revezai.modules.user.entites.UserEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity(name = "group_members")
@Data
public class GroupMemberEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private GroupEntity group;

    @Enumerated(EnumType.STRING)
    private GroupMemberStatus status;

    
}
