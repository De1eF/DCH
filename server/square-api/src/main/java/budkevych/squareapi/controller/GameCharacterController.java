package budkevych.squareapi.controller;

import budkevych.squareapi.dto.TimestampResponseDto;
import budkevych.squareapi.model.GameCharacter;
import budkevych.squareapi.service.CharacterService;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/characters")
@AllArgsConstructor
public class GameCharacterController {
    private final CharacterService characterService;

    @GetMapping("/inject")
    public String update() {
        List<GameCharacter> gameCharacterList = new ArrayList<>();

        GameCharacter first = new GameCharacter();
        first.setLastUpdate(System.currentTimeMillis());
        first.setName("First");
        gameCharacterList.add(first);

        GameCharacter second = new GameCharacter();
        second.setLastUpdate(System.currentTimeMillis());
        second.setName("Toost");
        gameCharacterList.add(second);

        GameCharacter third = new GameCharacter();
        third.setLastUpdate(System.currentTimeMillis());
        third.setName("Third");
        gameCharacterList.add(third);

        characterService.saveAll(gameCharacterList);

        return "Success!";
    }

    @GetMapping("/check-update")
    public TimestampResponseDto update(@RequestParam Long id) {
        TimestampResponseDto timestampResponseDto = new TimestampResponseDto();
        timestampResponseDto.setTimestamp(characterService.find(id).getLastUpdate());
        return timestampResponseDto;
    }
}
