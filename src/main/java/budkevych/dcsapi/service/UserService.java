package budkevych.dcsapi.service;

import budkevych.dcsapi.model.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.PageRequest;

public interface UserService {
    User add(User user);

    User update(Long id, User user);

    Optional<User> findByEmail(String email);

    User findById(Long id);

    List<User> findByUsername(String username, PageRequest pageRequest);
}
