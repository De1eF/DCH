package budkevych.dcsapi.dto.response;

import java.util.Map;
import lombok.Data;

@Data
public class GameCharacterResponseDto {
    private Long id;
    private Long timestamp;
    private Long userId;
    private String name;
    private Map<String, Object> paramMap;
}
