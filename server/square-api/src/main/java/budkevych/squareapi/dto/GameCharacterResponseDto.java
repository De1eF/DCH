package budkevych.squareapi.dto;

import lombok.Data;

@Data
public class GameCharacterResponseDto {
    private String id;
    private Long timestamp;
    private String name;
}
