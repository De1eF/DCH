package budkevych.dcsapi.security;

import budkevych.dcsapi.exception.AuthenticationException;
import budkevych.dcsapi.model.User;
import org.springframework.security.core.Authentication;

public interface AuthenticationService {
    User register(String email, String username, String password);

    User login(String login, String password) throws AuthenticationException;

    User getAuthenticated(Authentication auth);
}
