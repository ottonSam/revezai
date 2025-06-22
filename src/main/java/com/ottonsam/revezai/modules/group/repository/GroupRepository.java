package com.ottonsam.revezai.modules.group.repository;

import java.util.UUID;


import org.springframework.data.jpa.repository.JpaRepository;

import com.ottonsam.revezai.modules.group.entities.GroupEntity;

public interface GroupRepository extends JpaRepository<GroupEntity, UUID> {

    
}
