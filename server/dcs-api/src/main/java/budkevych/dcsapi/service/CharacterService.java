package budkevych.dcsapi.service;

import budkevych.dcsapi.model.GameCharacter;
import java.util.List;

public interface CharacterService {
    GameCharacter find(Long id, Short isDeleted, boolean loadParamMap);

    List<GameCharacter> findAllByUserId(Long userId, boolean loadParamMap);

    Long countAllByUserId(Long userId);

    GameCharacter save(GameCharacter gameCharacter);

    GameCharacter update(Long id, GameCharacter gameCharacter);

    void delete(Long id);

    void permanentDelete(Long id);

    GameCharacter recover(Long id);
}
