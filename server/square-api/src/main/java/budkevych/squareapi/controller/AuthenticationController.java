package budkevych.squareapi.controller;

import budkevych.squareapi.config.ConfigProperties;
import budkevych.squareapi.dto.mapper.UserMapper;
import budkevych.squareapi.dto.request.UserLoginRequestDto;
import budkevych.squareapi.dto.request.UserRequestDto;
import budkevych.squareapi.dto.response.UserResponseDto;
import budkevych.squareapi.exception.AuthenticationException;
import budkevych.squareapi.model.User;
import budkevych.squareapi.security.AuthenticationService;
import budkevych.squareapi.security.jwt.JwtTokenProvider;
import budkevych.squareapi.service.UserService;
import budkevych.squareapi.service.impl.MailService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequiredArgsConstructor
public class AuthenticationController {
    private static final String LOGIN_ENDPOINT = "/login-email.html?token=";

    private final ConfigProperties configProperties;
    private final AuthenticationService authenticationService;
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserMapper userMapper;
    private final MailService mailService;

    @PostMapping("/login")
    @CrossOrigin
    @Operation(summary = "login as an existing user")
    public ResponseEntity<?> login(@RequestBody UserLoginRequestDto userLoginDto) {
        User user;
        try {
            user = authenticationService.login(userLoginDto.getLogin(),
                    userLoginDto.getPassword());
        } catch (AuthenticationException e) {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body("Incorrect login or password");
        }
        String token = jwtTokenProvider.createToken(user.getUsername(), user.getRoles().stream()
                .map(role -> role.getRoleName().name())
                .collect(Collectors.toList()));
        Map<Object, Object> response = new HashMap<>();
        response.put("login", userLoginDto.getLogin());
        response.put("token", token);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login-email")
    @CrossOrigin
    @Operation(summary = "login as an existing user")
    public ResponseEntity<?> emailLogin(@RequestBody UserLoginRequestDto userLoginDto) {
        User user;
        try {
            user = userService
                    .findByEmail(userLoginDto.getLogin())
                    .orElseThrow(NoSuchElementException::new);
        } catch (NoSuchElementException e) {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body("There is no such email in our database");
        }
        String token = jwtTokenProvider.createToken(user.getUsername(), user.getRoles().stream()
                .map(role -> role.getRoleName().name())
                .collect(Collectors.toList()));
        try {
            mailService.sendEmail(
                    userLoginDto.getLogin(),
                    "Your one click authentication",
                    "Click <a href='%s%s%s'>here</a> to authenticate"
                            .formatted(configProperties.getAddress(), LOGIN_ENDPOINT, token)
            );
        } catch (MessagingException e) {
            throw new RuntimeException("Unable to email to " + userLoginDto.getLogin(), e);
        }

        Map<Object, Object> response = new HashMap<>();
        response.put("login", userLoginDto.getLogin());
        response.put("token", token);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    @Operation(summary = "register a user")
    public UserResponseDto register(@RequestBody @Valid UserRequestDto requestDto) {
        User user = authenticationService.register(
                requestDto.getEmail(),
                requestDto.getUsername(),
                requestDto.getPassword());
        return userMapper.mapToDto(user);
    }
}
