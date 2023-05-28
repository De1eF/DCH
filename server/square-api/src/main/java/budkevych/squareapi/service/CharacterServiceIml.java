package budkevych.squareapi.service;

import budkevych.squareapi.model.GameCharacter;
import budkevych.squareapi.repository.GameCharacterRepository;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CharacterServiceIml implements CharacterService {
    private final GameCharacterRepository gameCharacterRepository;

    @Override
    public GameCharacter find(Long id) {
        return gameCharacterRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Unable to find game character by id " + id));
    }

    @Override
    public void save(GameCharacter gameCharacter) {
        gameCharacterRepository.save(gameCharacter);
    }

    @Override
    public void saveAll(List<GameCharacter> gameCharacterList) {
        gameCharacterRepository.saveAll(gameCharacterList);
    }
}
