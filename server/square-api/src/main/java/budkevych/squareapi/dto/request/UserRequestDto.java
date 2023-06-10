package budkevych.squareapi.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserRequestDto {
    @NotBlank
    private String email;
    @NotBlank
    private String username;
    @NotBlank
    private String password;
}
