package com.burialsociety.member_service.service;

import com.burialsociety.member_service.entity.SystemUser;
import java.util.List;

public interface SystemUserService {
    List<SystemUser> getAllUsers();

    SystemUser getUserById(Long id);

    SystemUser createUser(SystemUser user);

    SystemUser updateUser(Long id, SystemUser user);

    void toggleMfa(Long id, Boolean enabled);

    void resetPassword(Long id);
}
