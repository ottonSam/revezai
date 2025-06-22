package com.ottonsam.revezai.modules.group.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ottonsam.revezai.modules.group.entities.GroupMemberEntity;
import com.ottonsam.revezai.modules.group.entities.GroupMemberStatus;

public interface GroupMemberRepository extends JpaRepository<GroupMemberEntity, UUID> {

    List<GroupMemberEntity> findAllByUserIdAndStatus(UUID userId, GroupMemberStatus status);
    Optional<GroupMemberEntity> findByUserIdAndGroupId(UUID userId, UUID groupId);
}
