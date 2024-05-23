package com.example.service.role;

import com.example.entity.Role;

import java.util.List;


public interface RoleService {
    Role addRole(Role role);
    List<Role> getAllRole();
    void deleteRole(int roleId);
    Role findById(int roleId);
}
