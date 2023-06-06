package budkevych.squareapi.controller;

import budkevych.squareapi.dto.mapper.GameCharacterMapper;
import budkevych.squareapi.dto.request.GameCharacterRequestDto;
import budkevych.squareapi.dto.response.GameCharacterResponseDto;
import budkevych.squareapi.dto.response.TimestampResponseDto;
import budkevych.squareapi.model.GameCharacter;
import budkevych.squareapi.model.User;
import budkevych.squareapi.service.CharacterService;
import budkevych.squareapi.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
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
    private final UserService userService;

    @GetMapping("/check-update/{id}")
    @CrossOrigin
    @Operation(summary = "checks if incoming object is up to date, returns new version if not")
    public TimestampResponseDto getUpToDate(@PathVariable Long id,
                                            @RequestParam Long timestamp) {
        GameCharacter gameCharacter = characterService.find(id);
        TimestampResponseDto timestampResponseDto = new TimestampResponseDto();
        timestampResponseDto.setTimestamp(gameCharacter.getLastUpdate());
        if (!gameCharacter.getLastUpdate().equals(timestamp)) {
            timestampResponseDto.setObject(mapper.toDto(gameCharacter));
        }
        return timestampResponseDto;
    }

    @GetMapping("{id}")
    @Operation(summary = "get character by id")
    public GameCharacterResponseDto get(@PathVariable Long id) {
        return mapper.toDto(characterService.find(id));
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
    public ResponseEntity<?> add(@RequestBody GameCharacterRequestDto dto) {
        GameCharacter gameCharacter = mapper.toModel(dto);
        Optional<User> forUser = userService
                .findByUsername(SecurityContextHolder.getContext()
                        .getAuthentication()
                        .getName());
        if (forUser.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NO_CONTENT)
                    .body("Unable to save, can't find a current user");
        }
        if (characterService.countAllByUserId(forUser.get().getId()) >= 10) {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body("You cannot create more than 10 characters");
        }
        gameCharacter.setUserId(forUser.get().getId());
        return ResponseEntity
                .ok(mapper.toDto(characterService.save(gameCharacter)));
    }

    @PutMapping("{id}")
    @Operation(summary = "update object in db \n"
            + "if user isn't specified in the body, will take currently logged in one")
    public ResponseEntity<?> update(@PathVariable Long id,
                                    @RequestBody GameCharacterRequestDto dto) {
        GameCharacter gameCharacter = mapper.toModel(dto);
        Optional<User> forUser = userService
                .findByUsername(SecurityContextHolder.getContext()
                        .getAuthentication()
                        .getName());
        if (forUser.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NO_CONTENT)
                    .body("Unable to update, can't find a current user");
        }
        gameCharacter.setUserId(dto.getUserId() == null
                ? forUser.get().getId()
                : dto.getUserId());
        return ResponseEntity
                .ok(mapper.toDto(characterService.update(id, gameCharacter)));
    }
}
