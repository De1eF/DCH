package budkevych.squareapi.service;

import budkevych.squareapi.model.GameCharacter;
import java.util.List;

public interface CharacterService {
    GameCharacter find(Long id);

    void save(GameCharacter gameCharacter);

    void saveAll(List<GameCharacter> gameCharacterList);

    void update(Long id, GameCharacter gameCharacter);
}
