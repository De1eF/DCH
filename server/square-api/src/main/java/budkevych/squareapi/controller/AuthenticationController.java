package budkevych.squareapi.controller;

import budkevych.squareapi.dto.mapper.UserMapper;
import budkevych.squareapi.dto.request.UserLoginRequestDto;
import budkevych.squareapi.dto.request.UserRequestDto;
import budkevych.squareapi.dto.response.UserResponseDto;
import budkevych.squareapi.exception.AuthenticationException;
import budkevych.squareapi.model.User;
import budkevych.squareapi.security.AuthenticationService;
import budkevych.squareapi.security.jwt.JwtTokenProvider;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = {"http://93.175.234.30:5500/", "http://127.0.0.1:5500"})
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserMapper userMapper;

    @PostMapping("/login")
    @Operation(summary = "login as an existing user")
    public ResponseEntity<?> login(@RequestBody UserLoginRequestDto userLoginDto)
            throws AuthenticationException {
        User user = authenticationService.login(userLoginDto.getLogin(),
                userLoginDto.getPassword());
        String token = jwtTokenProvider.createToken(user.getUsername(), user.getRoles().stream()
                .map(role -> role.getRoleName().name())
                .collect(Collectors.toList()));
        Map<Object, Object> response = new HashMap<>();
        response.put("username", userLoginDto.getLogin());
        response.put("token", token);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    @Operation(summary = "register a user")
    public UserResponseDto register(@RequestBody @Valid UserRequestDto requestDto) {
        User user = authenticationService.register(
                requestDto.getUsername(),
                requestDto.getPassword()
        );
        return userMapper.mapToDto(user);
    }
}
