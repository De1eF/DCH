package budkevych.dcsapi.service;

import budkevych.dcsapi.model.UserRole;

public interface RoleService {
    UserRole add(UserRole role);

    UserRole findByRoleName(String roleName);
}
