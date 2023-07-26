package budkevych.dcsapi.service;

import budkevych.dcsapi.model.GameCharacter;
import budkevych.dcsapi.model.OwnershipRequest;
import java.util.List;

public interface CharacterService {
    GameCharacter find(Long id, Short isDeleted, boolean loadParamMap);

    List<GameCharacter> findAllByUserId(Long userId);

    Long countAllByUserId(Long userId);

    GameCharacter save(GameCharacter gameCharacter);

    GameCharacter update(Long id, GameCharacter gameCharacter);

    Long countRequests(Long ownerId);

    void requestOwnership(Long id, Long requesterId, Long ownerId);

    void acceptOwnership(Long requestId);

    void denyOwnership(Long requestId);

    OwnershipRequest getOwnershipRequest(Long requestId);

    List<OwnershipRequest> getOwnershipRequests(Long ownerId);

    GameCharacter addOwner(Long id, Long userId);

    GameCharacter removeOwner(Long id, Long userId);

    void delete(Long id);

    void permanentDelete(Long id);

    GameCharacter recover(Long id);
}
