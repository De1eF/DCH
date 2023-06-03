package budkevych.squareapi.service;

import budkevych.squareapi.model.User;
import java.util.Optional;

public interface UserService {
    User add(User user);

    void update(User user);

    Optional<User> findByUsername(String username);

    Optional<User> findById(Long id);
}
