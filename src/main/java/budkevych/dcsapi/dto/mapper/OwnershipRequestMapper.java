package budkevych.dcsapi.dto.mapper;

import budkevych.dcsapi.dto.request.OwnershipRequestDto;
import budkevych.dcsapi.dto.response.OwnershipRequestResponseDto;
import budkevych.dcsapi.model.OwnershipRequest;
import org.springframework.stereotype.Component;

@Component
public class OwnershipRequestMapper {
    public OwnershipRequestResponseDto toDto(OwnershipRequest ownershipRequest) {
        OwnershipRequestResponseDto dto = new OwnershipRequestResponseDto();
        dto.setId(ownershipRequest.getId());
        dto.setRequesterId(ownershipRequest.getRequesterId());
        dto.setOwnerId(ownershipRequest.getOwnerId());
        dto.setCharacterId(ownershipRequest.getCharacterId());
        return dto;
    }

    public OwnershipRequest toModel(OwnershipRequestDto dto) {
        OwnershipRequest model = new OwnershipRequest();
        model.setOwnerId(dto.getOwnerId());
        model.setCharacterId(dto.getCharacterId());
        return model;
    }
}
