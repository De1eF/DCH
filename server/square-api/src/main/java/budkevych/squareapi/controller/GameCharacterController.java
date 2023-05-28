package budkevych.squareapi.controller;

import budkevych.squareapi.dto.TimestampResponseDto;
import budkevych.squareapi.model.GameCharacter;
import budkevych.squareapi.service.CharacterService;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/characters")
@CrossOrigin("http://127.0.0.1:5500")
@AllArgsConstructor
public class GameCharacterController {
    private final CharacterService characterService;

    @GetMapping("/check-update")
    public TimestampResponseDto update(@RequestParam Long id) {
        TimestampResponseDto timestampResponseDto = new TimestampResponseDto();
        timestampResponseDto.setTimestamp(characterService.find(id).getLastUpdate());
        return timestampResponseDto;
    }
}
