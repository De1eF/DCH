package budkevych.squareapi.security;

import budkevych.squareapi.exception.AuthenticationException;
import budkevych.squareapi.model.User;
import org.springframework.security.core.Authentication;

public interface AuthenticationService {
    User register(String email, String username, String password);

    User login(String login, String password) throws AuthenticationException;

    User getAuthenticated(Authentication auth);
}
