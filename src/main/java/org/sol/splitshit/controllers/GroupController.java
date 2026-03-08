package org.sol.splitshit.controllers;

import org.jspecify.annotations.NonNull;
import org.sol.splitshit.models.Group;
import org.sol.splitshit.models.SUser;
import org.sol.splitshit.repos.GroupRepo;
import org.sol.splitshit.repos.UserRepo;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/groups")
@PreAuthorize("isAuthenticated()")
class GroupController {
    private UserRepo userRepo;
    private GroupRepo groupRepo;

    public GroupController(UserRepo userRepo,
                           GroupRepo groupRepo) {
        this.userRepo = userRepo;
        this.groupRepo = groupRepo;
    }

    @PostMapping
    public ResponseEntity<@NonNull Group> createGroup(@AuthenticationPrincipal String username,
                                                      @RequestParam String name) {
        var optUser = userRepo.findById(username);
        if(optUser.isEmpty()) return ResponseEntity.notFound().build();
        SUser user = optUser.get();
        Group group = Group.builder().name(name).member(user).build();
        user.setGroup(group);
        group = groupRepo.save(group);
        userRepo.save(user);
        return ResponseEntity.ok(group);
    }

    @PostMapping("/{groupId}")
    public ResponseEntity<?> joinGroup(@AuthenticationPrincipal String username,
                                       @PathVariable Long groupId) {
        var optUser = userRepo.findById(username);
        if(optUser.isEmpty()) return ResponseEntity.notFound().build();
        SUser user = optUser.get();
        if(user.getGroup() != null) return ResponseEntity.badRequest().body("You are already in a group");
        var optGroup = groupRepo.findById(groupId);
        if(optGroup.isEmpty()) return ResponseEntity.notFound().build();
        Group group = optGroup.get();
        group.addMember(user);
        user.setGroup(group);
        groupRepo.save(group);
        userRepo.save(user);
        return ResponseEntity.ok(group);
    }

    @DeleteMapping
    public ResponseEntity<?> leaveGroup(@AuthenticationPrincipal String username) {
        var optUser = userRepo.findById(username);
        if(optUser.isEmpty()) return ResponseEntity.notFound().build();
        SUser user = optUser.get();
        if(user.getGroup() == null) return ResponseEntity.badRequest().body("Not in a group");
        user.setGroup(null);
        userRepo.save(user);
        return ResponseEntity.ok().build();
    }
}
