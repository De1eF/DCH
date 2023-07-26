package budkevych.dcsapi.dto.mapper;

import budkevych.dcsapi.dto.request.OwnershipRequestDto;
import budkevych.dcsapi.dto.response.OwnershipRequestResponseDto;
import budkevych.dcsapi.model.GameCharacter;
import budkevych.dcsapi.model.OwnershipRequest;
import budkevych.dcsapi.model.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class OwnershipRequestMapper {
    private final GameCharacterMapper characterMapper;
    private final UserMapper userMapper;

    public OwnershipRequestResponseDto toDto(Long id,
                                             Long ownerId,
                                             User requester,
                                             GameCharacter character) {
        OwnershipRequestResponseDto dto = new OwnershipRequestResponseDto();
        dto.setId(id);
        dto.setRequester(userMapper.mapToDto(requester));
        dto.setOwnerId(ownerId);
        dto.setCharacter(characterMapper.toDto(character));
        return dto;
    }

    public OwnershipRequest toModel(OwnershipRequestDto dto) {
        OwnershipRequest model = new OwnershipRequest();
        model.setOwnerId(dto.getOwnerId());
        model.setCharacterId(dto.getCharacterId());
        return model;
    }
}
