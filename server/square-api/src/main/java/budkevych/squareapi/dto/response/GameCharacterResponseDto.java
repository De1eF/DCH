package budkevych.squareapi.dto.response;

import lombok.Data;

@Data
public class GameCharacterResponseDto {
    private Long id;
    private Long timestamp;
    private Long userId;
    private String name;
    private String paramMap;
}
