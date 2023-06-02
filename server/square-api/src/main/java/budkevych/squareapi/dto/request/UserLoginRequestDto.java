package budkevych.squareapi.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserLoginRequestDto {
    @NotBlank(message = "Login can't be null or blank!")
    private String login;
    @NotBlank(message = "Password can't be null or blank!")
    private String password;
}
