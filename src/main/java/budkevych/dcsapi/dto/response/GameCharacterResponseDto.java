package budkevych.dcsapi.dto.response;

import java.util.List;
import java.util.Map;
import lombok.Data;

@Data
public class GameCharacterResponseDto {
    private Long id;
    private Long timestamp;
    private List<UserResponseDto> owners;
    private String name;
    private Long portraitId;
    private Map<String, Object> data;
    private Map<String, Object> paramMap;
}
