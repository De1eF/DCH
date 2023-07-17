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
        return userRepository.findUserById(id)
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
    public User update(Long id, User user) {
        User old = findById(id);
        old.setUsername(user.getUsername() == null
                ? old.getUsername()
                : user.getUsername());
        old.setPassword(user.getPassword() == null
                ? old.getPassword()
                : user.getPassword());
        old.setPortraitId(user.getPortraitId() == null
                ? old.getPortraitId()
                : user.getPortraitId());
        old.setRoles(user.getRoles() == null
                ? old.getRoles()
                : user.getRoles());
        userRepository.save(old);
        return old;
    }
}
