package budkevych.squareapi.service.impl;

import budkevych.squareapi.exception.ResourceNotFoundException;
import budkevych.squareapi.model.User;
import budkevych.squareapi.repository.UserRepository;
import budkevych.squareapi.service.UserService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public User add(User user) {
        return userRepository.save(user);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "User not found for id " + id));
    }

    @Override
    public void update(Long id, User user) {
        user.setId(id);

        userRepository.save(user);
    }
}
