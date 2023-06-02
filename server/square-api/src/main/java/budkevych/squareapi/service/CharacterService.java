package budkevych.squareapi.service;

import budkevych.squareapi.model.GameCharacter;
import java.util.List;

public interface CharacterService {
    GameCharacter find(Long id);

    List<GameCharacter> findAllByUserId(Long userId);

    GameCharacter save(GameCharacter gameCharacter);

    void saveAll(List<GameCharacter> gameCharacterList);

    void update(Long id, GameCharacter gameCharacter);
}
