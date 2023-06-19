package budkevych.squareapi.controller;

import budkevych.squareapi.dto.mapper.GameCharacterMapper;
import budkevych.squareapi.dto.request.GameCharacterRequestDto;
import budkevych.squareapi.dto.response.GameCharacterResponseDto;
import budkevych.squareapi.dto.response.TimestampResponseDto;
import budkevych.squareapi.exception.NoAccessException;
import budkevych.squareapi.model.GameCharacter;
import budkevych.squareapi.model.User;
import budkevych.squareapi.model.UserRole;
import budkevych.squareapi.security.AuthenticationService;
import budkevych.squareapi.service.CharacterService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
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
@RequestMapping("/characters")
@CrossOrigin
@AllArgsConstructor
public class GameCharacterController {
    private final CharacterService characterService;
    private final GameCharacterMapper mapper;
    private final AuthenticationService authenticationService;

    @GetMapping("/check-update/{id}")
    @CrossOrigin
    @Operation(summary = "checks if incoming object is up to date, returns new version if not")
    public ResponseEntity<?> getUpToDate(@PathVariable Long id,
                                         @RequestParam Long timestamp) {
        GameCharacter gameCharacter = characterService.find(id, (short) 0);
        TimestampResponseDto timestampResponseDto = new TimestampResponseDto();
        timestampResponseDto.setTimestamp(gameCharacter.getLastUpdate());
        if (!gameCharacter.getLastUpdate().equals(timestamp)) {
            timestampResponseDto.setObject(mapper.toDto(gameCharacter));
        }
        return ResponseEntity.ok(timestampResponseDto);
    }

    @GetMapping("{id}")
    @Operation(summary = "get character by id")
    public ResponseEntity<?> get(@PathVariable Long id) {
        GameCharacter gameCharacter = characterService.find(id, (short) 0);
        return ResponseEntity.ok(mapper.toDto(gameCharacter));
    }

    @GetMapping("/for-user/{user-id}")
    @Operation(summary = "get all characters of specific user")
    public List<GameCharacterResponseDto> getForUser(@PathVariable("user-id") Long userId) {
        List<GameCharacter> gameCharacterList =
                characterService.findAllByUserId(userId);
        gameCharacterList.forEach(d -> d.setParamMap("{}"));
        return gameCharacterList
                .stream()
                .map(mapper::toDto)
                .toList();

    }

    @PostMapping()
    @Operation(summary = "save object to db \n "
            + "can't create more than 10 ")
    public ResponseEntity<?> add(Authentication auth,
                                 @RequestBody @Valid GameCharacterRequestDto dto) {
        GameCharacter gameCharacter = mapper.toModel(dto);
        User forUser = authenticationService.getAuthenticated(auth);
        if (characterService.countAllByUserId(forUser.getId()) >= 10) {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body("You cannot create more than 10 characters");
        }
        gameCharacter.setUserId(forUser.getId());
        return ResponseEntity
                .ok(mapper.toDto(characterService.save(gameCharacter)));
    }

    @PutMapping("{id}")
    @Operation(summary = "update object in db \n"
            + "if user isn't specified in the body, will take currently logged in one")
    public ResponseEntity<?> update(Authentication auth,
                                    @PathVariable Long id,
                                    @RequestBody @Valid GameCharacterRequestDto dto) {
        getAccessibleCharacter(auth, id);
        GameCharacter gameCharacter = mapper.toModel(dto);
        return ResponseEntity
                .ok(mapper.toDto(characterService.update(id, gameCharacter)));
    }

    @DeleteMapping("{id}")
    @Operation(summary = "soft delete character by id")
    public ResponseEntity<?> delete(Authentication auth,
                                    @PathVariable Long id) {
        GameCharacter gameCharacter = getAccessibleCharacter(auth, id);
        characterService.delete(id);
        return ResponseEntity
                .ok()
                .body(gameCharacter.getName() + " has been deleted");
    }

    @GetMapping("/recover/{id}")
    @Operation(summary = "recover character by id")
    public ResponseEntity<?> recover(@PathVariable Long id) {
        characterService.recover(id);
        return ResponseEntity
                .ok()
                .body("Object has been recover id: " + id);
    }

    private GameCharacter getAccessibleCharacter(Authentication auth,
                                                 Long id) {
        GameCharacter gameCharacter = characterService.find(id, (short) 0);
        User user = authenticationService.getAuthenticated(auth);
        if (!gameCharacter.getUserId().equals(user.getId())
                && user.getRoles().stream().noneMatch(
                    userRole -> userRole.getRoleName().equals(UserRole.RoleName.ADMIN))) {
            throw new NoAccessException("You can only access your characters");
        }
        return gameCharacter;
    }
}
