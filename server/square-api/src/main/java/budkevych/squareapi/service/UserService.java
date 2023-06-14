package budkevych.squareapi.service;

import budkevych.squareapi.model.User;
import java.util.List;
import java.util.Optional;

public interface UserService {
    User add(User user);

    void update(User user);

    List<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    Optional<User> findById(Long id);
}
