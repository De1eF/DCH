package budkevych.dcsapi.dto.response;

import java.time.LocalDateTime;
import java.util.Set;
import lombok.Data;

@Data
public class GameSessionResponseDto {
    private Long id;
    private String name;
    private Long timestamp;
    private Set<GameCharacterResponseDto> gameCharacters;
    private Set<UserResponseDto> users;
    private LocalDateTime createdOn;
}
