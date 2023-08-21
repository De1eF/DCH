package budkevych.dcsapi.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class OwnershipRequestDto {
    @NotNull
    @PositiveOrZero
    private Long characterId;
    @NotNull
    @PositiveOrZero
    private Long ownerId;
}
