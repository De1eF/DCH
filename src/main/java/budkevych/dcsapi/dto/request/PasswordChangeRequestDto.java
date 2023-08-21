package budkevych.dcsapi.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PasswordChangeRequestDto {
    @NotNull
    private String oldPassword;
    @NotNull
    private String newPassword;
}
