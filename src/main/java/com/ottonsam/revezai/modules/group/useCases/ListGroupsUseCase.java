package com.ottonsam.revezai.modules.group.useCases;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ottonsam.revezai.modules.group.dto.GroupResponseDTO;
import com.ottonsam.revezai.modules.group.entities.GroupEntity;
import com.ottonsam.revezai.modules.group.entities.GroupMemberStatus;
import com.ottonsam.revezai.modules.group.repository.GroupMemberRepository;
import com.ottonsam.revezai.modules.user.dto.UserResponseDTO;

@Service
public class ListGroupsUseCase {

    @Autowired
    private GroupMemberRepository groupMemberRepository;

    public List<GroupResponseDTO> execute(UUID userId) {
        
        var groupMembers = groupMemberRepository.findAllByUserIdAndStatus(userId, GroupMemberStatus.ACCEPTED);

        if (groupMembers.isEmpty()) {
            return List.of();
        }

        return groupMembers.stream()
            .map(groupMember -> {
                GroupEntity group = groupMember.getGroup();
                if (group.getMembers() == null) {
                    group.setMembers(List.of());
                }
                List<UserResponseDTO> members = group.getMembers().stream()
                    .filter(member -> member.getStatus() == GroupMemberStatus.ACCEPTED)
                    .map(member -> member.getUser())
                    .map(UserResponseDTO::new)
                    .toList();
                return new GroupResponseDTO(group, members);
            })
            .toList();
    }
}
