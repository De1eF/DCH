package budkevych.dcsapi.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OwnershipRequestDto {
    @NotNull
    private Long characterId;
    @NotNull
    private Long ownerId;
}
