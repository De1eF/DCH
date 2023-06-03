package budkevych.squareapi.controller;

import budkevych.squareapi.dto.mapper.UserMapper;
import budkevych.squareapi.dto.request.UserRequestDto;
import budkevych.squareapi.dto.request.UserRolesRequestDto;
import budkevych.squareapi.dto.response.UserResponseDto;
import budkevych.squareapi.model.User;
import budkevych.squareapi.model.UserRole;
import budkevych.squareapi.service.RoleService;
import budkevych.squareapi.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = {"http://93.175.234.30:5500/", "http://127.0.0.1:5500"})
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final RoleService roleService;
    private final UserMapper userMapper;

    @GetMapping("/me")
    @Operation(summary = "get currently logged in user")
    public UserResponseDto get(Authentication auth) {
        User user = getAuthenticated(auth);
        return userMapper.mapToDto(user);
    }

    @PutMapping("/me")
    @Operation(summary = "update logged in user's data")
    public UserResponseDto update(Authentication auth, @RequestBody UserRequestDto requestDto) {
        User user = userMapper.mapToModel(requestDto);
        User authenticatedUser = getAuthenticated(auth);
        user.setId(authenticatedUser.getId());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(authenticatedUser.getRoles());
        userService.update(user);
        return userMapper.mapToDto(user);
    }

    @PutMapping("/{id}/role")
    @Operation(summary = "update roles for a specific user")
    public UserResponseDto updateRoles(
            @PathVariable Long id,
            @RequestBody UserRolesRequestDto rolesRequest) {
        User user = userService.findById(id).orElseThrow(
                () -> new RuntimeException("User with id " + id + " not found"));
        Set<UserRole> newUserRoles = rolesRequest.getRoles().stream()
                .map(roleService::findByRoleName)
                .collect(Collectors.toSet());
        user.setRoles(newUserRoles);
        userService.update(user);
        return userMapper.mapToDto(user);
    }

    private User getAuthenticated(Authentication auth) {
        UserDetails details = (UserDetails) auth.getPrincipal();
        String email = details.getUsername();
        return userService.findByUsername(email).orElseThrow(
                () -> new RuntimeException("User with email " + email + " not found"));
    }
}
