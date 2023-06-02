package budkevych.squareapi.service;

import budkevych.squareapi.model.UserRole;

public interface RoleService {
    UserRole add(UserRole role);

    UserRole findByRoleName(String roleName);
}
