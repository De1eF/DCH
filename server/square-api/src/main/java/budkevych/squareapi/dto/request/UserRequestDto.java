package budkevych.squareapi.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserRequestDto {
    @NotBlank
    @NotNull
    @NotEmpty
    @Email
    private String email;
    @NotBlank
    @NotNull
    private String username;
    private String password;
}
