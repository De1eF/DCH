package budkevych.squareapi.service.impl;

import budkevych.squareapi.model.GameCharacter;
import budkevych.squareapi.repository.GameCharacterRepository;
import budkevych.squareapi.service.CharacterService;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CharacterServiceIml implements CharacterService {
    private final GameCharacterRepository gameCharacterRepository;

    @Override
    public Optional<GameCharacter> find(Long id, Short isDeleted) throws NoSuchElementException {
        return gameCharacterRepository.findByIdAndIsDeleted(id, isDeleted);
    }

    @Override
    public List<GameCharacter> findAllByUserId(Long userId) {
        return gameCharacterRepository.findAllByUserIdAndIsDeleted(userId, (short) 0);
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

    @Override
    public void delete(Long id) throws NoSuchElementException {
        GameCharacter gameCharacter =
                find(id, (short) 0).orElseThrow(NoSuchElementException::new);
        gameCharacter.setIsDeleted((short) 1);
        save(gameCharacter);
    }

    @Override
    public void permanentDelete(Long id) {
        gameCharacterRepository.deleteById(id);
    }

    @Override
    public GameCharacter recover(Long id) throws NoSuchElementException {
        GameCharacter gameCharacter =
                find(id, (short) 1).orElseThrow(NoSuchElementException::new);
        gameCharacter.setIsDeleted((short) 0);
        save(gameCharacter);
        return gameCharacter;
    }
}
