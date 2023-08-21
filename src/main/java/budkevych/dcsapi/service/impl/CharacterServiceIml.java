package budkevych.dcsapi.service.impl;

import budkevych.dcsapi.exception.ResourceNotFoundException;
import budkevych.dcsapi.model.GameCharacter;
import budkevych.dcsapi.model.OwnershipRequest;
import budkevych.dcsapi.model.ParamMap;
import budkevych.dcsapi.model.User;
import budkevych.dcsapi.repository.GameCharacterRepository;
import budkevych.dcsapi.repository.OwnershipRequestRepository;
import budkevych.dcsapi.service.CharacterService;
import budkevych.dcsapi.service.UserService;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CharacterServiceIml implements CharacterService {
    private final GameCharacterRepository gameCharacterRepository;
    private final UserService userService;
    private final OwnershipRequestRepository ownershipRepository;

    @Override
    public GameCharacter find(Long id, Short isDeleted, boolean loadParamMap, boolean loadOwners) {
        ParamMap paramMap = ParamMap.builder().id(id).data("{}").build();
        Set<User> owners = new HashSet<>();
        GameCharacter gameCharacter;
        if (loadParamMap) {
            if (loadOwners) {
                gameCharacter = gameCharacterRepository.findByIdAndIsDeletedWithParamMapAndOwners(id, isDeleted)
                        .orElseThrow(() -> new ResourceNotFoundException(
                                "Game character not found for id " + id));
            } else {
                gameCharacter = gameCharacterRepository.findByIdAndIsDeletedWithParamMap(id, isDeleted)
                        .orElseThrow(() -> new ResourceNotFoundException(
                                "Game character not found for id " + id));
                gameCharacter.setOwners(owners);
            }
        } else if (loadOwners) {
            gameCharacter = gameCharacterRepository.findByIdAndIsDeletedWithOwners(id, isDeleted)
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Game character not found for id " + id));
            gameCharacter.setParamMap(paramMap);
        } else {
            gameCharacter = gameCharacterRepository.findByIdAndIsDeleted(id, isDeleted)
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Game character not found for id " + id));
            gameCharacter.setParamMap(paramMap);
            gameCharacter.setOwners(owners);
        }
        setOwnersRoles(gameCharacter);
        return gameCharacter;
    }

    @Override
    public List<GameCharacter> findAllByUserId(Long userId, PageRequest pageRequest) {
        return gameCharacterRepository.findAllByUserIdAndIsDeleted(userId, (short) 0, pageRequest)
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
        gameCharacterRepository.save(gameCharacter);
        return gameCharacter;
    }

    @Override
    public GameCharacter update(Long id, GameCharacter gameCharacter) {
        GameCharacter oldGameCharacter = find(id, (short) 0, true, true);
        oldGameCharacter.setName(gameCharacter.getName());
        oldGameCharacter.setLastUpdate(System.currentTimeMillis());
        oldGameCharacter.setPortraitId(gameCharacter.getPortraitId());
        oldGameCharacter.setData(gameCharacter.getData());

        if (gameCharacter.getOwners() != null
                && !gameCharacter.getOwners().isEmpty()) {
            oldGameCharacter.setOwners(gameCharacter.getOwners());
        }

        ParamMap paramMap = oldGameCharacter.getParamMap();
        String oldParamMapData = paramMap.getData();
        String newParamMapData = gameCharacter.getParamMap().getData();
        paramMap.setData(replaceValues(oldParamMapData, newParamMapData));
        oldGameCharacter.setParamMap(paramMap);

        gameCharacterRepository.save(oldGameCharacter);
        return oldGameCharacter;
    }

    @Override
    public GameCharacter addOwner(Long id, Long userId) {
        GameCharacter oldGameCharacter = find(id, (short) 0, true, true);
        oldGameCharacter.getOwners().add(userService.findById(userId));
        gameCharacterRepository.save(oldGameCharacter);
        return oldGameCharacter;
    }

    @Override
    public GameCharacter removeOwner(Long id, Long userId) {
        GameCharacter oldGameCharacter = find(id, (short) 0, true, true);
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
    public Long countRequests(Long ownerId) {
        return ownershipRepository.countByOwnerId(ownerId);
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
        GameCharacter gameCharacter = find(id, (short) 0, false, false);
        gameCharacter.setIsDeleted((short) 1);
        save(gameCharacter);
    }

    @Override
    public void permanentDelete(Long id) {
        gameCharacterRepository.deleteById(id);
    }

    @Override
    public GameCharacter recover(Long id) throws NoSuchElementException {
        GameCharacter gameCharacter = find(id, (short) 1, false, false);
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

    private static String replaceValues(String old, String nw) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");

        old = old.substring(1, old.length() - 1);
        String[] toReplaceWith = nw.substring(1, nw.length() - 1).split(",");

        String[] toReplace = old.split(",");
        for(int i = 0; i < toReplace.length; i++) {
            String[] keyValue = toReplace[i].split(":", 2);
            String key = keyValue[0];
            for (String toReplaceWithObj : toReplaceWith) {
                String[] toReplaceWithKeyVale =  toReplaceWithObj.split(":", 2);
                String toReplaceWithKey = toReplaceWithKeyVale[0];

                if (key.equals(toReplaceWithKey)) {
                    toReplace[i] = toReplaceWithObj;
                }
            }
            stringBuilder.append(toReplace[i]).append(i == toReplace.length - 1 ? "" : ",");
        }
        return stringBuilder.append("}").toString();
    }
}
