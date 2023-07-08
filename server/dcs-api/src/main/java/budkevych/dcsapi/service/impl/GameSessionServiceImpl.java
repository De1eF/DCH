package budkevych.dcsapi.service.impl;

import budkevych.dcsapi.exception.ResourceNotFoundException;
import budkevych.dcsapi.model.GameCharacter;
import budkevych.dcsapi.model.GameSession;
import budkevych.dcsapi.model.User;
import budkevych.dcsapi.repository.GameSessionRepository;
import budkevych.dcsapi.service.CharacterService;
import budkevych.dcsapi.service.GameSessionService;
import java.util.List;
import java.util.Set;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GameSessionServiceImpl implements GameSessionService {
    private final GameSessionRepository gameSessionRepository;
    private final CharacterService characterService;
    private final UserServiceImpl userService;

    @Override
    public GameSession find(Long id) {
        GameSession gameSession =
                gameSessionRepository
                        .findByIdFetched(id)
                        .orElseThrow(() -> new ResourceNotFoundException(""));
        return gameSession;
    }

    @Override
    public List<GameSession> findAllByUserId(Long userId) {
        List<GameSession> gameSessions = gameSessionRepository.findAllByUser(userId);
        gameSessions.forEach(s -> s.setCharactersInSession(Set.of()));
        return gameSessions;
    }

    @Override
    public GameSession add(GameSession gameSession, User originalUser) {
        gameSession.setLastUpdate(System.currentTimeMillis());
        gameSession.setUsers(Set.of(originalUser));
        return gameSessionRepository.save(gameSession);
    }

    @Override
    public void permanentDelete(Long id) {
        gameSessionRepository.deleteById(id);
    }

    @Override
    public GameSession updateName(Long id, String name) {
        GameSession gameSession = find(id);
        gameSession.setSessionName(name);
        gameSessionRepository.save(gameSession);
        return gameSession;
    }

    @Override
    public GameSession addCharacter(Long id, Long characterId) {
        GameSession gameSession = find(id);
        GameCharacter gameCharacter = characterService.find(characterId, (short) 0, false);
        gameSession.getCharactersInSession().add(gameCharacter);
        gameSession.getUsers().add(userService.findById(gameCharacter.getUserId()));
        gameSessionRepository.save(gameSession);
        return gameSession;
    }
}
