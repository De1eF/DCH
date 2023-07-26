package budkevych.dcsapi.dto.response;

import lombok.Data;

@Data
public class OwnershipRequestResponseDto {
    private Long id;
    private GameCharacterResponseDto character;
    private UserResponseDto requester;
    private Long ownerId;
}
