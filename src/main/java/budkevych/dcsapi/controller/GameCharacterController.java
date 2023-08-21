package budkevych.dcsapi.controller;

import budkevych.dcsapi.dto.mapper.GameCharacterMapper;
import budkevych.dcsapi.dto.mapper.OwnershipRequestMapper;
import budkevych.dcsapi.dto.response.CountDto;
import budkevych.dcsapi.dto.request.GameCharacterRequestDto;
import budkevych.dcsapi.dto.request.OwnershipRequestDto;
import budkevych.dcsapi.dto.response.ActionResponseDto;
import budkevych.dcsapi.dto.response.GameCharacterResponseDto;
import budkevych.dcsapi.dto.response.OwnershipRequestResponseDto;
import budkevych.dcsapi.dto.response.TimestampResponseDto;
import budkevych.dcsapi.exception.NoAccessException;
import budkevych.dcsapi.model.GameCharacter;
import budkevych.dcsapi.model.User;
import budkevych.dcsapi.model.UserRole;
import budkevych.dcsapi.security.AuthenticationService;
import budkevych.dcsapi.service.CharacterService;
import budkevych.dcsapi.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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
    private final OwnershipRequestMapper ownershipRequestMapper;
    private final UserService userService;

    @GetMapping("/check-update/{id}")
    @CrossOrigin
    @Operation(summary = "checks if incoming object is up to date, returns new version if not")
    public ResponseEntity<?> getUpToDate(@PathVariable Long id,
                                         @RequestParam Long timestamp) {
        GameCharacter gameCharacter = characterService.find(id, (short) 0, false, false);
        TimestampResponseDto timestampResponseDto = new TimestampResponseDto();
        timestampResponseDto.setTimestamp(gameCharacter.getLastUpdate());
        if (!gameCharacter.getLastUpdate().equals(timestamp)) {
            timestampResponseDto.setObject(mapper.toDto(
                    characterService.find(id, (short) 0, true, true)));
        }
        return ResponseEntity.ok(timestampResponseDto);
    }

    @GetMapping("{id}")
    @Operation(summary = "get character by id")
    public ResponseEntity<?> get(@PathVariable Long id) {
        GameCharacter gameCharacter = characterService.find(id, (short) 0, false, true);
        return ResponseEntity.ok(mapper.toDto(gameCharacter));
    }

    @GetMapping("/for-user/{user-id}")
    @Operation(summary = "get all characters of specific user")
    public List<GameCharacterResponseDto> getForUser(@PathVariable("user-id")
                                                     Long userId,
                                                     @RequestParam(defaultValue = "0")
                                                     Integer page,
                                                     @RequestParam(defaultValue = "20")
                                                     Integer count) {
        Sort.Order order = new Sort.Order(Sort.Direction.DESC, "lastUpdate");
        Sort sort = Sort.by(order);
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        List<GameCharacter> gameCharacterList =
                characterService.findAllByUserId(userId, pageRequest);
        return gameCharacterList
                .stream()
                .map(mapper::toDto)
                .toList();

    }

    @PostMapping()
    @Operation(summary = "save object to db \n "
            + "can't create more than 10")
    public ResponseEntity<?> add(Authentication auth,
                                 @RequestBody @Valid GameCharacterRequestDto dto) {
        GameCharacter gameCharacter = mapper.toModel(dto);
        User forUser = authenticationService.getAuthenticated(auth);
        if (characterService.countAllByUserId(forUser.getId()) >= 10) {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body("You cannot create more than 10 characters");
        }
        gameCharacter.getOwners().add(forUser);
        return ResponseEntity
                .ok(mapper.toDto(characterService.save(gameCharacter)));
    }

    @PutMapping("/{id}")
    @Operation(summary = "update object in db \n"
            + "if user isn't specified in the body, will take currently logged in one")
    public GameCharacterResponseDto update(Authentication auth,
                                           @PathVariable Long id,
                                           @RequestBody @Valid GameCharacterRequestDto dto) {
        getAccessibleCharacter(auth, id);
        GameCharacter gameCharacter = mapper.toModel(dto);
        return mapper.toDto(characterService.update(id, gameCharacter));
    }

    @PutMapping("/{id}/portrait")
    @Operation(summary = "only update portrait")
    public GameCharacterResponseDto updatePortrait(Authentication auth,
                                                   @PathVariable Long id,
                                                   @RequestBody @Valid GameCharacterRequestDto dto) {
        getAccessibleCharacter(auth, id);
        GameCharacter gameCharacter = characterService.find(id, (short) 0, true, true);
        gameCharacter.setPortraitId(dto.getPortraitId());
        return mapper.toDto(characterService.update(id, gameCharacter));
    }

    @PatchMapping("/owners/{id}")
    @Operation(summary = "accept ownership request")
    public ActionResponseDto acceptOwnership(Authentication auth,
                                             @PathVariable Long id) {
        checkOwnershipRequest(auth, id);
        characterService.acceptOwnership(id);
        return ActionResponseDto
                .builder()
                .message("Ownership added")
                .build();
    }

    @GetMapping("/owners/requests/count")
    @Operation(summary = "get count of incoming requests")
    public CountDto countOwnershipRequests(Authentication auth) {
        User user = authenticationService.getAuthenticated(auth);
        return CountDto
                .builder()
                .count(characterService.countRequests(user.getId()))
                .build();
    }

    @GetMapping("/owners/requests")
    @Operation(summary = "get your requests")
    public List<OwnershipRequestResponseDto> getOwnershipRequests(Authentication auth) {
        User user = authenticationService.getAuthenticated(auth);
        return characterService.getOwnershipRequests(user.getId())
                .stream()
                .map(r -> ownershipRequestMapper.toDto(
                        r.getId(),
                        r.getOwnerId(),
                        userService.findById(r.getRequesterId()),
                        characterService.find(r.getCharacterId(), (short) 0, false, false)
                ))
                .toList();
    }

    @PutMapping("/owners/requests")
    @Operation(summary = "request ownership")
    public ActionResponseDto requestOwnership(Authentication auth,
                                              @RequestBody @Valid OwnershipRequestDto dto) {
        try {
            getAccessibleCharacter(auth, dto.getCharacterId());
            return ActionResponseDto
                    .builder()
                    .message("You are already an owner")
                    .build();
        } catch (NoAccessException e) {
            User user = authenticationService.getAuthenticated(auth);
            if (characterService.getOwnershipRequests(dto.getOwnerId())
                    .stream()
                    .anyMatch(r -> r.getRequesterId().equals(user.getId())
                            && r.getCharacterId().equals(dto.getCharacterId()))) {
                return ActionResponseDto
                        .builder()
                        .message("Already requested")
                        .build();
            }
            characterService.requestOwnership(dto.getCharacterId(),
                    user.getId(), dto.getOwnerId());
            return ActionResponseDto
                    .builder()
                    .message("Ownership requested")
                    .build();
        }
    }

    @DeleteMapping("/owners/requests/{id}")
    @Operation(summary = "deny ownership request")
    public ActionResponseDto denyOwnership(Authentication auth,
                                           @PathVariable Long id) {
        checkOwnershipRequest(auth, id);
        characterService.denyOwnership(id);
        return ActionResponseDto
                .builder()
                .message("Ownership denied")
                .build();
    }

    @DeleteMapping("/{id}/owners/{userId}")
    @Operation(summary = "remove owner")
    public ActionResponseDto removeOwner(Authentication auth,
                                         @PathVariable Long id,
                                         @PathVariable Long userId) {
        getAccessibleCharacter(auth, id);
        if (!characterService.find(id, (short) 0, false, true).getId().equals(id)) {
            return ActionResponseDto
                    .builder()
                    .message("User %s doesn't own character %s".formatted(userId, id))
                    .build();
        }
        characterService.removeOwner(id, userId);
        return ActionResponseDto
                .builder()
                .message("Ownership removed for user %s".formatted(userId))
                .build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "soft delete character by id")
    public ActionResponseDto delete(Authentication auth,
                                    @PathVariable Long id) {
        getAccessibleCharacter(auth, id);
        characterService.delete(id);
        return ActionResponseDto.builder().message("Character deleted").build();
    }

    @GetMapping("/recover/{id}")
    @Operation(summary = "recover character by id")
    public ResponseEntity<?> recover(@PathVariable Long id) {
        characterService.recover(id);
        return ResponseEntity
                .ok(ActionResponseDto.builder().message("Character recovered").build());
    }

    private void checkOwnershipRequest(Authentication auth, Long requestId) {
        User user = authenticationService.getAuthenticated(auth);
        if (!characterService.getOwnershipRequest(requestId).getOwnerId().equals(user.getId())) {
            throw new NoAccessException("Not your request");
        }
    }

    private GameCharacter getAccessibleCharacter(Authentication auth,
                                                 Long id) {
        GameCharacter gameCharacter = characterService.find(id, (short) 0, false, true);
        User user = authenticationService.getAuthenticated(auth);
        if (!gameCharacter.getOwners().contains(user)
                && user.getRoles().stream().noneMatch(
                userRole -> userRole.getRoleName().equals(UserRole.RoleName.ADMIN))) {
            throw new NoAccessException("You can only access your characters");
        }
        return gameCharacter;
    }
}
