package com.ottonsam.revezai.modules.group.useCases;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ottonsam.revezai.modules.group.entities.GroupMemberEntity;
import com.ottonsam.revezai.modules.group.entities.GroupMemberStatus;
import com.ottonsam.revezai.modules.group.repository.GroupMemberRepository;
import com.ottonsam.revezai.modules.group.repository.GroupRepository;
import com.ottonsam.revezai.modules.user.repositories.UserRepository;

@Service
public class InviteUseCase {
    
    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GroupMemberRepository groupMemberRepository;

    public void execute(UUID groupId, String username, UUID userId) {

        var group = groupRepository.findById(groupId)
            .orElseThrow(() -> new RuntimeException("Group not found"));

        var invitedUser = userRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("Invited user not found"));

        if (!group.getOwner().getId().equals(userId)) {
            throw new RuntimeException("Only the group owner can invite users");
        }

        var existingMember = groupMemberRepository.findByUserIdAndGroupId(invitedUser.getId(), groupId);
        if (existingMember.isPresent()) {
            throw new RuntimeException("User is already a member or has a pending invitation");
        }

        var groupMember = new GroupMemberEntity();
        groupMember.setGroup(group);
        groupMember.setUser(invitedUser);
        groupMember.setStatus(GroupMemberStatus.PENDING);
        groupMemberRepository.save(groupMember);
    }
}
