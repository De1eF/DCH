package budkevych.squareapi.dto.request;

import lombok.Data;

@Data
public class GameCharacterRequestDto {
    private Long userId;
    private String name;
    private String paramMap;
}
