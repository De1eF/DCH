package budkevych.dcsapi.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class GameSessionRequestDto {
    @NotBlank
    private String name;
}
