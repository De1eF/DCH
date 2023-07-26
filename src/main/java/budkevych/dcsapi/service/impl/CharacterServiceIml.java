package budkevych.dcsapi.service.impl;

import budkevych.dcsapi.exception.ResourceNotFoundException;
import budkevych.dcsapi.model.GameCharacter;
import budkevych.dcsapi.model.OwnershipRequest;
import budkevych.dcsapi.model.ParamMap;
import budkevych.dcsapi.repository.GameCharacterRepository;
import budkevych.dcsapi.repository.OwnershipRequestRepository;
import budkevych.dcsapi.service.CharacterService;
import budkevych.dcsapi.service.UserService;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CharacterServiceIml implements CharacterService {
    private final GameCharacterRepository gameCharacterRepository;
    private final UserService userService;
    private final OwnershipRequestRepository ownershipRepository;

    @Override
    public GameCharacter find(Long id, Short isDeleted, boolean loadParamMap) {
        if (loadParamMap) {
            GameCharacter gameCharacter =
                    gameCharacterRepository.findByIdAndIsDeletedWithParamMap(id, isDeleted)
                            .orElseThrow(() -> new ResourceNotFoundException(
                                    "Game character not found for id " + id));
            setOwnersRoles(gameCharacter);
            return gameCharacter;
        }
        GameCharacter gameCharacter =
                gameCharacterRepository.findByIdAndIsDeleted(id, isDeleted)
                        .orElseThrow(() -> new ResourceNotFoundException(
                                "Game character not found for id " + id));
        //param map isn't loaded from the db, still has to be set to an empty one
        gameCharacter.setParamMap(ParamMap.builder().id(id).data("{}").build());
        setOwnersRoles(gameCharacter);
        return gameCharacter;
    }

    @Override
    public List<GameCharacter> findAllByUserId(Long userId) {
        //param map isn't loaded from the db, still has to be set to an empty one
        return gameCharacterRepository.findAllByUserIdAndIsDeleted(userId, (short) 0)
                .stream()
                .peek(c -> {
                    c.setParamMap(ParamMap.builder().id(c.getId()).data("{}").build());
                    setOwnersRoles(c);
                }).toList();
    }

    @Override
    public Long countAllByUserId(Long userId) {
        return gameCharacterRepository.countAllByOwners(userId);
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
        if (gameCharacter.getOwners() != null
                && !gameCharacter.getOwners().isEmpty()) {
            oldGameCharacter.setOwners(gameCharacter.getOwners());
        }
        oldGameCharacter.setParamMap(paramMap);
        oldGameCharacter.setLastUpdate(System.currentTimeMillis());
        oldGameCharacter.setPortraitId(gameCharacter.getPortraitId());
        gameCharacterRepository.save(oldGameCharacter);
        return oldGameCharacter;
    }

    @Override
    public GameCharacter addOwner(Long id, Long userId) {
        GameCharacter oldGameCharacter = find(id, (short) 0, true);
        oldGameCharacter.getOwners().add(userService.findById(userId));
        gameCharacterRepository.save(oldGameCharacter);
        return oldGameCharacter;
    }

    @Override
    public GameCharacter removeOwner(Long id, Long userId) {
        GameCharacter oldGameCharacter = find(id, (short) 0, true);
        oldGameCharacter.getOwners().remove(userService.findById(userId));
        gameCharacterRepository.save(oldGameCharacter);
        return oldGameCharacter;
    }

    @Override
    public void requestOwnership(Long id, Long requesterId, Long ownerId) {
        OwnershipRequest ownershipRequest = new OwnershipRequest();
        ownershipRequest.setCharacterId(id);
        ownershipRequest.setRequesterId(requesterId);
        ownershipRequest.setOwnerId(ownerId);
        ownershipRepository.save(ownershipRequest);
    }

    @Override
    public OwnershipRequest getOwnershipRequest(Long requestId) {
        return ownershipRepository.findById(requestId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Ownership request not found for id " + requestId));
    }

    @Override
    public void acceptOwnership(Long requestId) {
        OwnershipRequest request = getOwnershipRequest(requestId);
        addOwner(request.getCharacterId(), request.getRequesterId());
        denyOwnership(requestId);
    }

    @Override
    public void denyOwnership(Long requestId) {
        ownershipRepository.deleteById(requestId);
    }

    @Override
    public List<OwnershipRequest> getOwnershipRequests(Long ownerId) {
        return ownershipRepository.findAllByOwnerId(ownerId);
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

    private static void setOwnersRoles(GameCharacter gameCharacter) {
        if (gameCharacter.getOwners() == null) {
            gameCharacter.setOwners(new HashSet<>());
            return;
        }
        gameCharacter.setOwners(gameCharacter.getOwners()
                .stream()
                .peek(o -> o.setRoles(new HashSet<>()))
                .collect(Collectors.toSet()));
    }
}
