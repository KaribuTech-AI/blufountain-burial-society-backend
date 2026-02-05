package com.burialsociety.member_service.service.impl;

import com.burialsociety.member_service.entity.SystemUser;
import com.burialsociety.member_service.repository.SystemUserRepository;
import com.burialsociety.member_service.service.SystemUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SystemUserServiceImpl implements SystemUserService {
    private final SystemUserRepository systemUserRepository;

    @Override
    public List<SystemUser> getAllUsers() {
        return systemUserRepository.findAll();
    }

    @Override
    public SystemUser getUserById(Long id) {
        return systemUserRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public SystemUser createUser(SystemUser user) {
        user.setPasswordHash("TEMP_HASH"); // In real world, hash the password
        user.setStatus("ACTIVE");
        user.setMfaEnabled(false);
        return systemUserRepository.save(user);
    }

    @Override
    public SystemUser updateUser(Long id, SystemUser user) {
        SystemUser existing = getUserById(id);
        existing.setName(user.getName());
        existing.setEmail(user.getEmail());
        existing.setRole(user.getRole());
        return systemUserRepository.save(existing);
    }

    @Override
    public void toggleMfa(Long id, Boolean enabled) {
        SystemUser user = getUserById(id);
        user.setMfaEnabled(enabled);
        systemUserRepository.save(user);
    }

    @Override
    public void resetPassword(Long id) {
        SystemUser user = getUserById(id);
        user.setPasswordHash("TEMP_RESET_HASH"); // In real world, generate secure token
        user.setLastLogin(null);
        systemUserRepository.save(user);
    }
}
