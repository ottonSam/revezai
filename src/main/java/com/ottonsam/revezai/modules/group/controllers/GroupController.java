package com.ottonsam.revezai.modules.group.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ottonsam.revezai.modules.group.dto.InviteRequestDTO;
import com.ottonsam.revezai.modules.group.dto.ResponseToInvitationRequestDTO;
import com.ottonsam.revezai.modules.group.entities.GroupEntity;
import com.ottonsam.revezai.modules.group.useCases.CreateGroupUseCase;
import com.ottonsam.revezai.modules.group.useCases.DeleteGroupUseCase;
import com.ottonsam.revezai.modules.group.useCases.DetailGroupUseCase;
import com.ottonsam.revezai.modules.group.useCases.InviteUseCase;
import com.ottonsam.revezai.modules.group.useCases.ListGroupsUseCase;
import com.ottonsam.revezai.modules.group.useCases.ListInvitationsUseCase;
import com.ottonsam.revezai.modules.group.useCases.ResponseInvitationUseCase;
import com.ottonsam.revezai.modules.group.useCases.UpdateGroupUseCase;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/v1/groups")
public class GroupController {

    @Autowired
    private CreateGroupUseCase createGroupUseCase;

    @Autowired
    private ListGroupsUseCase listGroupsUseCase;

    @Autowired
    private DetailGroupUseCase detailGroupUseCase;

    @Autowired
    private UpdateGroupUseCase updateGroupUseCase;

    @Autowired
    private DeleteGroupUseCase deleteGroupUseCase;

    @Autowired
    private InviteUseCase inviteUseCase;

    @Autowired
    private ListInvitationsUseCase listInvitationsUseCase;

    @Autowired
    private ResponseInvitationUseCase responseInvitationUseCase;
    
    @PostMapping
    public ResponseEntity<Object> createGroup(@Valid @RequestBody GroupEntity entity, HttpServletRequest request) {
        try {
            String userIdString = (String) request.getAttribute("user_id");
            return ResponseEntity.status(HttpStatus.CREATED)
                .body(createGroupUseCase.execute(entity, userIdString != null ? UUID.fromString(userIdString) : null));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping()
    public ResponseEntity<Object> listGroups(HttpServletRequest request) {
        try {
            String userIdString = (String) request.getAttribute("user_id");
            return ResponseEntity.ok()
                .body(listGroupsUseCase.execute(userIdString != null ? UUID.fromString(userIdString) : null));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> detailGroup(@PathVariable String id, HttpServletRequest request) {
        try {
            String userIdString = (String) request.getAttribute("user_id");
            return ResponseEntity.ok()
                .body(detailGroupUseCase.execute(UUID.fromString(id), userIdString != null ? UUID.fromString(userIdString) : null));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> editGroup(@PathVariable String id, @Valid @RequestBody GroupEntity entity, HttpServletRequest request) {
        try {
            String userIdString = (String) request.getAttribute("user_id");
            return ResponseEntity.ok()
                .body(updateGroupUseCase.execute(entity, UUID.fromString(id), userIdString != null ? UUID.fromString(userIdString) : null));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteGroup(@PathVariable String id, HttpServletRequest request) {
        try {
            String userIdString = (String) request.getAttribute("user_id");
            deleteGroupUseCase.execute(UUID.fromString(id), userIdString != null ? UUID.fromString(userIdString) : null);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/invite")
    public ResponseEntity<Object> InviteToGroup(@Valid @RequestBody InviteRequestDTO invite, HttpServletRequest request) {
        try {
            String userIdString = (String) request.getAttribute("user_id");
            inviteUseCase.execute(UUID.fromString(invite.getGroupId()), invite.getUsername(), userIdString != null ? UUID.fromString(userIdString) : null);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/invite")
    public ResponseEntity<Object> listInvitations(HttpServletRequest request) {
        try {
            String userIdString = (String) request.getAttribute("user_id");
            return ResponseEntity.ok()
                .body(listInvitationsUseCase.execute(userIdString != null ? UUID.fromString(userIdString) : null));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/invite/response")
    public ResponseEntity<Object> responseToInvitation(@Valid @RequestBody ResponseToInvitationRequestDTO entity, HttpServletRequest request) {
        try {
            String userIdString = (String) request.getAttribute("user_id");
            responseInvitationUseCase.execute(entity, userIdString != null ? UUID.fromString(userIdString) : null);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
}
