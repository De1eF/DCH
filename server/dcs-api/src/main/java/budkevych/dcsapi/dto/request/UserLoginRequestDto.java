package budkevych.dcsapi.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserLoginRequestDto {
    @NotBlank(message = "Login can't be null or blank!")
    private String login;
    @Size(min = 5, message = "Password must be at least 5 symbols")
    private String password;
}
