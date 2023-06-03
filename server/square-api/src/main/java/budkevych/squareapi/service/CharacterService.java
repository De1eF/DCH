package budkevych.squareapi.service;

import budkevych.squareapi.model.GameCharacter;
import java.util.List;

public interface CharacterService {
    GameCharacter find(Long id);

    List<GameCharacter> findAllByUserId(Long userId);

    Long countAllByUserId(Long userId);

    GameCharacter save(GameCharacter gameCharacter);

    void saveAll(List<GameCharacter> gameCharacterList);

    GameCharacter update(Long id, GameCharacter gameCharacter);
}
