package budkevych.squareapi.security;

import budkevych.squareapi.exception.AuthenticationException;
import budkevych.squareapi.model.User;
import budkevych.squareapi.service.RoleService;
import budkevych.squareapi.service.UserService;
import java.util.Optional;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserService userService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User register(String username, String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setRoles(Set.of(roleService.findByRoleName("CUSTOMER")));
        return userService.add(user);
    }

    @Override
    public User login(String username, String password) throws AuthenticationException {
        Optional<User> userFromDb = userService.findByEmail(username);
        if (userFromDb.isEmpty()
                || !passwordEncoder.matches(password, userFromDb.get().getPassword())) {
            throw new AuthenticationException("Incorrect username or password!!!");
        }
        return userFromDb.get();
    }
}
