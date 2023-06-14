package budkevych.squareapi.service;

import budkevych.squareapi.model.GameCharacter;
import java.util.List;
import java.util.Optional;

public interface CharacterService {
    Optional<GameCharacter> find(Long id, Short isDeleted);

    List<GameCharacter> findAllByUserId(Long userId);

    Long countAllByUserId(Long userId);

    GameCharacter save(GameCharacter gameCharacter);

    void saveAll(List<GameCharacter> gameCharacterList);

    GameCharacter update(Long id, GameCharacter gameCharacter);

    void delete(Long id);

    void permanentDelete(Long id);

    GameCharacter recover(Long id);
}
