package budkevych.squareapi.dto.response;

import java.util.List;
import lombok.Data;

@Data
public class UserResponseDto {
    private Long id;
    private String username;
    private String password;
    private List<RoleResponseDto> roles;
}
