package budkevych.dcsapi.controller;

import budkevych.dcsapi.config.ConfigProperties;
import budkevych.dcsapi.dto.mapper.UserMapper;
import budkevych.dcsapi.dto.request.UserRequestDto;
import budkevych.dcsapi.dto.request.UserRolesRequestDto;
import budkevych.dcsapi.dto.response.ActionResponseDto;
import budkevych.dcsapi.dto.response.UserResponseDto;
import budkevych.dcsapi.model.User;
import budkevych.dcsapi.security.AuthenticationService;
import budkevych.dcsapi.service.FileService;
import budkevych.dcsapi.service.RoleService;
import budkevych.dcsapi.service.UserService;
import budkevych.dcsapi.service.impl.MailService;
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
    private static final String MAIL_HTML =
            "src/main/resources/mail/mailPasswordChange.html";

    private final UserService userService;
    private final RoleService roleService;
    private final UserMapper userMapper;
    private final AuthenticationService authenticationService;
    private final MailService mailService;
    private final FileService fileService;
    private final ConfigProperties configProperties;
    private final PasswordEncoder passwordEncoder;

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

    @GetMapping("/me/email-password-change")
    @Operation(summary = "send email with link to change password")
    public ActionResponseDto emailPasswordChange(Authentication auth) {
        User authenticatedUser = authenticationService.getAuthenticated(auth);
        mailService.sendEmail(
                authenticatedUser.getEmail(),
                "Password change",
                fileService.readAll(MAIL_HTML)
                        .formatted(configProperties.getAddress(), authenticatedUser.getEmail()));
        return ActionResponseDto.builder().message("Email sent").build();
    }

    @PutMapping("/me")
    @Operation(summary = "update logged in user's data")
    public UserResponseDto update(Authentication auth, @RequestBody UserRequestDto requestDto) {
        User authenticatedUser = authenticationService.getAuthenticated(auth);
        User user = userMapper.mapToModel(requestDto);
        user.setEmail(null);
        user.setPassword(null);
        return userMapper.mapToDto(userService.update(authenticatedUser.getId(), user));
    }

    @PutMapping("/me/change-password")
    @Operation(summary = "update password for user")
    public UserResponseDto updatePassword(Authentication auth,
                                          @RequestBody UserRequestDto requestDto) {
        User user = userMapper.mapToModel(requestDto);
        user.setUsername(null);
        user.setPortraitId(null);
        user.setEmail(null);
        user.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        User authenticatedUser = authenticationService.getAuthenticated(auth);
        return userMapper.mapToDto(userService.update(authenticatedUser.getId(), user));
    }

    @PutMapping("/{id}/role")
    @Operation(summary = "update roles for a specific user")
    public UserResponseDto updateRoles(
            @PathVariable Long id,
            @RequestBody UserRolesRequestDto rolesRequest) {
        User user = new User();
        user.setRoles(rolesRequest.getRoles().stream()
                .map(roleService::findByRoleName)
                .collect(Collectors.toSet()));
        return userMapper.mapToDto(userService.update(id, user));
    }
}
