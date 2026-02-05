package com.burialsociety.member_service.controller;

import com.burialsociety.member_service.entity.SystemUser;
import com.burialsociety.member_service.service.SystemUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class SystemUserController {
    private final SystemUserService systemUserService;

    @GetMapping
    public ResponseEntity<List<SystemUser>> getAllUsers() {
        return ResponseEntity.ok(systemUserService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SystemUser> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(systemUserService.getUserById(id));
    }

    @PostMapping
    public ResponseEntity<SystemUser> createUser(@RequestBody SystemUser user) {
        return ResponseEntity.ok(systemUserService.createUser(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SystemUser> updateUser(@PathVariable Long id, @RequestBody SystemUser user) {
        return ResponseEntity.ok(systemUserService.updateUser(id, user));
    }

    @PutMapping("/{id}/mfa")
    public ResponseEntity<Void> toggleMfa(@PathVariable Long id, @RequestBody Map<String, Boolean> body) {
        systemUserService.toggleMfa(id, body.get("enabled"));
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/reset-password")
    public ResponseEntity<Void> resetPassword(@PathVariable Long id) {
        systemUserService.resetPassword(id);
        return ResponseEntity.ok().build();
    }
}
