package budkevych.dcsapi.controller;

import budkevych.dcsapi.dto.mapper.GameSessionMapper;
import budkevych.dcsapi.dto.request.GameSessionRequestDto;
import budkevych.dcsapi.dto.response.ActionResponseDto;
import budkevych.dcsapi.dto.response.GameSessionResponseDto;
import budkevych.dcsapi.dto.response.TimestampResponseDto;
import budkevych.dcsapi.exception.NoAccessException;
import budkevych.dcsapi.model.GameSession;
import budkevych.dcsapi.model.User;
import budkevych.dcsapi.model.UserRole;
import budkevych.dcsapi.security.AuthenticationService;
import budkevych.dcsapi.service.GameSessionService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sessions")
@CrossOrigin
@AllArgsConstructor
public class GameSessionController {
    private final GameSessionService gameSessionService;
    private final GameSessionMapper mapper;
    private final AuthenticationService authenticationService;

    @GetMapping("/check-update/{id}")
    @CrossOrigin
    @Operation(summary = "checks if incoming object is up to date, returns new version if not")
    public ResponseEntity<?> getUpToDate(@PathVariable Long id,
                                         @RequestParam Long timestamp) {
        GameSession gameSession = gameSessionService.find(id);
        TimestampResponseDto timestampResponseDto = new TimestampResponseDto();
        timestampResponseDto.setTimestamp(gameSession.getLastUpdate());
        if (!gameSession.getLastUpdate().equals(timestamp)) {
            timestampResponseDto.setObject(mapper.toDto(gameSession));
        }
        return ResponseEntity.ok(timestampResponseDto);
    }

    @GetMapping("{id}")
    @Operation(summary = "get session by id")
    public ResponseEntity<?> get(@PathVariable Long id) {
        GameSession gameSession = gameSessionService.find(id);
        return ResponseEntity.ok(mapper.toDto(gameSession));
    }

    @GetMapping("/for-user/{user-id}")
    @Operation(summary = "get all sessions of specific user")
    public List<GameSessionResponseDto> getForUser(@PathVariable("user-id") Long userId) {
        List<GameSession> gameCharacterList =
                gameSessionService.findAllByUserId(userId);
        return gameCharacterList
                .stream()
                .map(mapper::toDto)
                .toList();

    }

    @PostMapping()
    @Operation(summary = "save object to db")
    public ResponseEntity<?> add(Authentication auth,
                                 @RequestBody @Valid GameSessionRequestDto dto) {
        GameSession gameSession = mapper.toModel(dto);
        User forUser = authenticationService.getAuthenticated(auth);
        return ResponseEntity
                .ok(mapper.toDto(gameSessionService.add(gameSession, forUser)));
    }

    @PutMapping("/name/{id}")
    @Operation(summary = "update object's name in db")
    public ResponseEntity<?> updateName(Authentication auth,
                                    @PathVariable Long id,
                                    @RequestParam String sessionName) {
        getAccessibleSessions(auth, id);
        return ResponseEntity
                .ok(mapper.toDto(gameSessionService.updateName(id, sessionName)));
    }

    @PutMapping("/add-character/{id}")
    @Operation(summary = "update characters within a session in db")
    public ResponseEntity<?> addCharacter(Authentication auth,
                                        @PathVariable Long id,
                                        @RequestParam Long characterId) {
        getAccessibleSessions(auth, id);
        return ResponseEntity
                .ok(mapper.toDto(gameSessionService.addCharacter(id, characterId)));
    }

    @DeleteMapping("{id}")
    @Operation(summary = "end session by id")
    public ResponseEntity<?> close(Authentication auth,
                                   @PathVariable Long id) {
        GameSession gameSession = getAccessibleSessions(auth, id);
        gameSessionService.permanentDelete(id);
        return ResponseEntity
                .ok(ActionResponseDto.builder().message("Session closed").build());
    }

    private GameSession getAccessibleSessions(Authentication auth,
                                                 Long id) {
        GameSession gameSession = gameSessionService.find(id);
        User user = authenticationService.getAuthenticated(auth);
        if (gameSession.getUsers().stream().map(User::getId).noneMatch(i -> i.equals(user.getId()))
                && user.getRoles().stream().noneMatch(
                    userRole -> userRole.getRoleName().equals(UserRole.RoleName.ADMIN))) {
            throw new NoAccessException("You don't have access to this session");
        }
        return gameSession;
    }
}
