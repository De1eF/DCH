package budkevych.dcsapi.service.impl;

import budkevych.dcsapi.exception.ResourceNotFoundException;
import budkevych.dcsapi.model.User;
import budkevych.dcsapi.repository.UserRepository;
import budkevych.dcsapi.service.UserService;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
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
    public List<User> findByUsername(String username,
                                     PageRequest pageRequest) {
        return userRepository.findAllByUsernameContains(username, pageRequest)
                .stream()
                .peek(u -> u.setRoles(new HashSet<>()))
                .collect(Collectors.toList());
    }

    @Override
    public void update(Long id, User user) {
        user.setId(id);
        userRepository.save(user);
    }
}
