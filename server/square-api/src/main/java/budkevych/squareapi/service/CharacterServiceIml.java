package budkevych.squareapi.service;

import budkevych.squareapi.model.GameCharacter;
import budkevych.squareapi.repository.GameCharacterRepository;
import jakarta.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CharacterServiceIml implements CharacterService {
    private final GameCharacterRepository gameCharacterRepository;

    @Override
    public GameCharacter find(String id) {
        return gameCharacterRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Unable to find game character by id " + id));
    }

    @Override
    public void save(GameCharacter gameCharacter) {
        gameCharacter.setLastUpdate(System.currentTimeMillis());
        gameCharacterRepository.save(gameCharacter);
    }

    @Override
    public void saveAll(List<GameCharacter> gameCharacterList) {
        gameCharacterList.forEach(gameCharacter ->
                gameCharacter.setLastUpdate(System.currentTimeMillis()));
        gameCharacterRepository.saveAll(gameCharacterList);
    }

    @Override
    public void update(String id, GameCharacter gameCharacter) {
        gameCharacter.setId(id);
        gameCharacter.setLastUpdate(System.currentTimeMillis());
        gameCharacterRepository.save(gameCharacter);
    }

    @PostConstruct
    public void update() {
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

        saveAll(gameCharacterList);
        System.out.println("Injected!");
    }
}
