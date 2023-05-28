package budkevych.squareapi.controller;

import budkevych.squareapi.dto.GameCharacterResponseDto;
import budkevych.squareapi.dto.TimestampResponseDto;
import budkevych.squareapi.mapper.GameCharacterMapper;
import budkevych.squareapi.model.GameCharacter;
import budkevych.squareapi.service.CharacterService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/characters")
@CrossOrigin(origins = {"http://93.175.234.30:5500/", "http://127.0.0.1:5500"})
@AllArgsConstructor
public class GameCharacterController {
    private final CharacterService characterService;
    private final GameCharacterMapper mapper;

    @GetMapping("/check-update")
    public GameCharacterResponseDto update(@RequestParam Long id,
                                           @RequestParam Long timestamp) {
        GameCharacter gameCharacter = characterService.find(id);
        if (gameCharacter.getLastUpdate().equals(timestamp)) {
            return null;
        }
        return mapper.toDto(gameCharacter);
    }
}
