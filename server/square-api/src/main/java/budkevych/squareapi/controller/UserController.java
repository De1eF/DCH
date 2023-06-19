package budkevych.squareapi.controller;

import budkevych.squareapi.dto.mapper.UserMapper;
import budkevych.squareapi.dto.request.UserRequestDto;
import budkevych.squareapi.dto.request.UserRolesRequestDto;
import budkevych.squareapi.dto.response.UserResponseDto;
import budkevych.squareapi.model.User;
import budkevych.squareapi.security.AuthenticationService;
import budkevych.squareapi.service.RoleService;
import budkevych.squareapi.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@CrossOrigin
@RequiredArgsConstructor
public class UserController {
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final RoleService roleService;
    private final UserMapper userMapper;
    private final AuthenticationService authenticationService;

    @GetMapping("/me")
    @Operation(summary = "get currently logged in user")
    public UserResponseDto get(Authentication auth) {
        User user = authenticationService.getAuthenticated(auth);
        return userMapper.mapToDto(user);
    }

    @PutMapping("/me")
    @Operation(summary = "update logged in user's data")
    public UserResponseDto update(Authentication auth, @RequestBody UserRequestDto requestDto) {
        User authenticatedUser = authenticationService.getAuthenticated(auth);
        User user = User.builder()
                .email(requestDto.getEmail())
                .username(requestDto.getUsername())
                .password(passwordEncoder.encode(requestDto.getPassword()))
                .roles(authenticatedUser.getRoles())
                .build();
        userService.update(authenticatedUser.getId(), user);
        return userMapper.mapToDto(user);
    }

    @PutMapping("/{id}/role")
    @Operation(summary = "update roles for a specific user")
    public UserResponseDto updateRoles(
            @PathVariable Long id,
            @RequestBody UserRolesRequestDto rolesRequest) {
        User user = userService.findById(id);
        user.setRoles(rolesRequest.getRoles().stream()
                .map(roleService::findByRoleName)
                .collect(Collectors.toSet()));
        userService.update(id, user);
        return userMapper.mapToDto(user);
    }
}
