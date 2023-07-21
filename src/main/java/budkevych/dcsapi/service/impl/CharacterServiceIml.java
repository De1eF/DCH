package budkevych.dcsapi.service.impl;

import budkevych.dcsapi.exception.ResourceNotFoundException;
import budkevych.dcsapi.model.GameCharacter;
import budkevych.dcsapi.model.ParamMap;
import budkevych.dcsapi.repository.GameCharacterRepository;
import budkevych.dcsapi.service.CharacterService;
import java.util.List;
import java.util.NoSuchElementException;
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
            return gameCharacterRepository.findByIdAndIsDeletedWithParamMap(id, isDeleted)
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Game character not found for id " + id));
        }
        GameCharacter character = gameCharacterRepository.findByIdAndIsDeleted(id, isDeleted)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Game character not found for id " + id));
        //param map isn't loaded from the db, still has to be set to an empty one
        character.setParamMap(ParamMap.builder().id(id).data("{}").build());
        return character;
    }

    @Override
    public List<GameCharacter> findAllByUserId(Long userId) {
        //param map isn't loaded from the db, still has to be set to an empty one
        return gameCharacterRepository.findAllByUserIdAndIsDeleted(userId, (short) 0)
                .stream()
                .peek(c -> c.setParamMap(ParamMap.builder().id(c.getId()).data("{}").build())
                ).toList();
    }

    @Override
    public Long countAllByUserId(Long userId) {
        return gameCharacterRepository.countAllByUserId(userId);
    }

    @Override
    public GameCharacter save(GameCharacter gameCharacter) {
        gameCharacter.setLastUpdate(System.currentTimeMillis());
        if (gameCharacter.getData() == null) {
            gameCharacter.setData("{}");
        }
        if (gameCharacter.getPortraitId() == null) {
            gameCharacter.setPortraitId(1L);
        }
        if (gameCharacter.getParamMap() == null) {
            gameCharacter.setParamMap(new ParamMap());
        }
        return gameCharacterRepository.save(gameCharacter);
    }

    @Override
    public GameCharacter update(Long id, GameCharacter gameCharacter)
            throws NoSuchElementException {
        GameCharacter oldGameCharacter = find(id, (short) 0, true);
        oldGameCharacter.setName(gameCharacter.getName());
        ParamMap paramMap = gameCharacter.getParamMap();
        paramMap.setId(id);
        oldGameCharacter.setParamMap(paramMap);
        oldGameCharacter.setLastUpdate(System.currentTimeMillis());
        oldGameCharacter.setPortraitId(gameCharacter.getPortraitId());
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
}
