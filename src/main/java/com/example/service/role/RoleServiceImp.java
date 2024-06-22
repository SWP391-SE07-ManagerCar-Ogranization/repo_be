package com.example.service.role;

import com.example.entity.Role;
import com.example.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImp implements RoleService {
    @Autowired
    RoleRepository roleRepository;

    @Override
    public Role addRole(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public List<Role> getAllRole() {
        return roleRepository.findAll();
    }

    @Override
    public void deleteRole(int roleId) {
        roleRepository.deleteById(roleId);
    }

    @Override
    public Role findById(int roleId) {
        return roleRepository.findById(roleId).orElse(null);
    }


}
