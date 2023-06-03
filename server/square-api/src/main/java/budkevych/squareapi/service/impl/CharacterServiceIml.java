package budkevych.squareapi.service.impl;

import budkevych.squareapi.model.GameCharacter;
import budkevych.squareapi.repository.GameCharacterRepository;
import budkevych.squareapi.service.CharacterService;
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
    public List<GameCharacter> findAllByUserId(Long userId) {
        return gameCharacterRepository.findAllByUserId(userId);
    }

    @Override
    public Long countAllByUserId(Long userId) {
        return gameCharacterRepository.countAllByUserId(userId);
    }

    @Override
    public GameCharacter save(GameCharacter gameCharacter) {
        gameCharacter.setLastUpdate(System.currentTimeMillis());
        return gameCharacterRepository.save(gameCharacter);
    }

    @Override
    public void saveAll(List<GameCharacter> gameCharacterList) {
        gameCharacterList.forEach(gameCharacter ->
                gameCharacter.setLastUpdate(System.currentTimeMillis()));
        gameCharacterRepository.saveAll(gameCharacterList);
    }

    @Override
    public GameCharacter update(Long id, GameCharacter gameCharacter) {
        gameCharacter.setId(id);
        gameCharacter.setLastUpdate(System.currentTimeMillis());
        return gameCharacterRepository.save(gameCharacter);
    }
}
