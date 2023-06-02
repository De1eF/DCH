package budkevych.squareapi.service.impl;

import budkevych.squareapi.model.UserRole;
import budkevych.squareapi.model.UserRole.RoleName;
import budkevych.squareapi.repository.RoleRepository;
import budkevych.squareapi.service.RoleService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Override
    public UserRole add(UserRole role) {
        return roleRepository.save(role);
    }

    @Override
    public UserRole findByRoleName(String roleName) {
        return roleRepository.findByRoleName(RoleName.valueOf(roleName.toUpperCase())).orElseThrow(
                () -> new EntityNotFoundException("Role not found with roleName: " + roleName));
    }
}
