package budkevych.dcsapi.service;

import budkevych.dcsapi.model.GameSession;
import budkevych.dcsapi.model.User;
import java.util.List;

public interface GameSessionService {
    GameSession find(Long id);

    List<GameSession> findAllByUserId(Long userId);

    GameSession add(GameSession gameCharacter, User originalUser);

    void permanentDelete(Long id);

    GameSession updateName(Long id, String name);

    GameSession addCharacter(Long id, Long characterId);
}
