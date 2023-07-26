package budkevych.dcsapi.dto.response;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OwnershipRequestResponseDto {
    private Long id;
    private Long characterId;
    private Long requesterId;
    private Long ownerId;
}
