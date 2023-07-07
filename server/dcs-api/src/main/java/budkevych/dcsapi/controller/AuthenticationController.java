package budkevych.dcsapi.controller;

import budkevych.dcsapi.config.ConfigProperties;
import budkevych.dcsapi.dto.mapper.UserMapper;
import budkevych.dcsapi.dto.request.UserLoginRequestDto;
import budkevych.dcsapi.dto.request.UserRequestDto;
import budkevych.dcsapi.dto.response.ActionResponseDto;
import budkevych.dcsapi.dto.response.LoginResponseDto;
import budkevych.dcsapi.dto.response.UserResponseDto;
import budkevych.dcsapi.exception.AuthenticationException;
import budkevych.dcsapi.model.User;
import budkevych.dcsapi.security.AuthenticationService;
import budkevych.dcsapi.security.jwt.JwtTokenProvider;
import budkevych.dcsapi.service.FileService;
import budkevych.dcsapi.service.UserService;
import budkevych.dcsapi.service.impl.MailService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequiredArgsConstructor
public class AuthenticationController {
    private static final String MAIL_HTML =
            "server/dcs-api/src/main/resources/mail/mailAuthentication.html";

    private final ConfigProperties configProperties;
    private final AuthenticationService authenticationService;
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserMapper userMapper;
    private final MailService mailService;
    private final FileService fileService;

    @PostMapping("/login")
    @CrossOrigin
    @Operation(summary = "login as an existing user with basic authentication")
    public ResponseEntity<?> login(@RequestBody @Valid UserLoginRequestDto userLoginDto) {
        User user = authenticationService.login(userLoginDto.getLogin(),
                userLoginDto.getPassword());
        String token = jwtTokenProvider.createToken(user.getEmail(), user.getRoles().stream()
                .map(role -> role.getRoleName().name())
                .collect(Collectors.toList()));
        LoginResponseDto response = new LoginResponseDto();
        response.setEmail(userLoginDto.getLogin());
        response.setToken(token);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login-email")
    @CrossOrigin
    @Operation(summary = "login as an existing user with email confirmation only")
    public ResponseEntity<?> emailOnlyLogin(@RequestBody @Valid UserLoginRequestDto userLoginDto) {
        User user = userService
                .findByEmail(userLoginDto.getLogin())
                .orElseThrow(() -> new AuthenticationException("Unknown email"));
        String token = jwtTokenProvider.createToken(user.getEmail(), user.getRoles().stream()
                .map(role -> role.getRoleName().name())
                .collect(Collectors.toList()));
        mailService.sendEmail(
                userLoginDto.getLogin(),
                "Your one click authentication",
                fileService.readAll(MAIL_HTML)
                        .formatted(configProperties.getAddress(), token));
        return ResponseEntity
                .ok(ActionResponseDto.builder().message("Email sent"));
    }

    @PostMapping("/register")
    @Operation(summary = "register a user")
    public UserResponseDto register(@Valid @RequestBody UserRequestDto requestDto) {
        User user = authenticationService.register(
                requestDto.getEmail(),
                requestDto.getUsername(),
                requestDto.getPassword());
        return userMapper.mapToDto(user);
    }
}
