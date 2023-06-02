package budkevych.squareapi.config;

import budkevych.squareapi.model.User;
import budkevych.squareapi.model.UserRole;
import budkevych.squareapi.model.UserRole.RoleName;
import budkevych.squareapi.service.RoleService;
import budkevych.squareapi.service.UserService;
import jakarta.annotation.PostConstruct;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer {
    private final UserService userService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init() {
        UserRole customerRole = new UserRole();
        customerRole.setRoleName(RoleName.CUSTOMER);
        roleService.add(customerRole);
        UserRole managerRole = new UserRole();
        managerRole.setRoleName(RoleName.MANAGER);
        roleService.add(managerRole);

        User manager = new User();
        manager.setUsername("admin");
        manager.setPassword(passwordEncoder.encode("1290"));
        manager.setRoles(Set.of(managerRole));
        userService.add(manager);
    }
}
