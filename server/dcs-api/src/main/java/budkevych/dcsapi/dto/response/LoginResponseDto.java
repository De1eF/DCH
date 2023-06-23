package budkevych.dcsapi.dto.response;

import lombok.Data;

@Data
public class LoginResponseDto {
    private String email;
    private String token;
}
