package budkevych.dcsapi.service.impl;

import budkevych.dcsapi.exception.ResourceNotFoundException;
import budkevych.dcsapi.model.GameCharacter;
import budkevych.dcsapi.model.ParamMap;
import budkevych.dcsapi.repository.GameCharacterRepository;
import budkevych.dcsapi.service.CharacterService;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CharacterServiceIml implements CharacterService {
    private static final String DEFAULT_PORTRAIT =
            "server/dcs-api/src/main/resources/pictures/portrait-default.png";

    private final GameCharacterRepository gameCharacterRepository;

    @Override
    public GameCharacter find(Long id, Short isDeleted, boolean loadParamMap) {
        if (loadParamMap) {
            return gameCharacterRepository.findByIdAndIsDeleted(id, isDeleted)
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Game character not found for id " + id));
        }
        Object[] o = (Object[]) gameCharacterRepository
                .findByIdAndIsDeletedNotLoadingParamMap(id, isDeleted)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Game character not found for id " + id));
        return mapFieldListToObject(o);
    }

    @Override
    public List<GameCharacter> findAllByUserId(Long userId, boolean loadParamMap) {
        if (loadParamMap) {
            return gameCharacterRepository.findAllByUserIdAndIsDeleted(userId, (short) 0);
        }
        return gameCharacterRepository
                .findAllByUserIdAndIsDeletedNotLoadingParamMap(userId, (short) 0)
                .stream()
                .map(this::mapFieldListToObject)
                .collect(Collectors.toList());
    }

    @Override
    public Long countAllByUserId(Long userId) {
        return gameCharacterRepository.countAllByUserId(userId);
    }

    @Override
    public GameCharacter save(GameCharacter gameCharacter) {
        gameCharacter.setLastUpdate(System.currentTimeMillis());
        if (gameCharacter.getPortraitId() == null) {
            gameCharacter.setPortraitId(0L);
        }
        return gameCharacter;
    }

    @Override
    public GameCharacter update(Long id, GameCharacter gameCharacter)
            throws NoSuchElementException {
        GameCharacter oldGameCharacter = find(id, (short) 0, true);
        oldGameCharacter.setName(gameCharacter.getName());
        oldGameCharacter.setParamMap(gameCharacter.getParamMap());
        oldGameCharacter.setLastUpdate(System.currentTimeMillis());
        return gameCharacterRepository.save(oldGameCharacter);
    }

    @Override
    public void delete(Long id) {
        GameCharacter gameCharacter = find(id, (short) 0, false);
        gameCharacter.setIsDeleted((short) 1);
        save(gameCharacter);
    }

    @Override
    public void permanentDelete(Long id) {
        gameCharacterRepository.deleteById(id);
    }

    @Override
    public GameCharacter recover(Long id) throws NoSuchElementException {
        GameCharacter gameCharacter = find(id, (short) 1, false);
        gameCharacter.setIsDeleted((short) 0);
        save(gameCharacter);
        return gameCharacter;
    }

    private GameCharacter mapFieldListToObject(Object[] o) {
        GameCharacter gameCharacter = new GameCharacter();
        gameCharacter.setId((Long) o[0]);
        gameCharacter.setLastUpdate((Long) o[1]);
        gameCharacter.setUserId((Long) o[2]);
        gameCharacter.setName((String) o[3]);
        gameCharacter.setIsDeleted((Short) o[4]);
        ParamMap paramMap = new ParamMap();
        paramMap.setData("{}");
        gameCharacter.setParamMap(paramMap);
        return gameCharacter;
    }
}
