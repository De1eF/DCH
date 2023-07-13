package budkevych.dcsapi.controller;

import budkevych.dcsapi.dto.mapper.UserMapper;
import budkevych.dcsapi.dto.request.UserRequestDto;
import budkevych.dcsapi.dto.request.UserRolesRequestDto;
import budkevych.dcsapi.dto.response.UserResponseDto;
import budkevych.dcsapi.model.User;
import budkevych.dcsapi.security.AuthenticationService;
import budkevych.dcsapi.service.RoleService;
import budkevych.dcsapi.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @GetMapping("/find")
    @Operation(summary = "find by username with pagination")
    public List<UserResponseDto> findByUsername(@RequestParam String username,
                                                @RequestParam(defaultValue = "0")
                                                Integer page,
                                                @RequestParam(defaultValue = "20")
                                                Integer count) {
        PageRequest pageRequest = PageRequest.of(page, count);
        return userService.findByUsername(username, pageRequest)
                .stream()
                .map(userMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @PutMapping("/me")
    @Operation(summary = "update logged in user's data")
    public UserResponseDto update(Authentication auth, @RequestBody UserRequestDto requestDto) {
        User authenticatedUser = authenticationService.getAuthenticated(auth);
        User user = new User();
        user.setEmail(requestDto.getEmail());
        user.setUsername(requestDto.getUsername());
        user.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        user.setRoles(authenticatedUser.getRoles());
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
