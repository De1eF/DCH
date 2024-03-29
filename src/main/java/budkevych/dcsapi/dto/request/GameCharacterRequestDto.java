package budkevych.dcsapi.dto.request;

import jakarta.validation.constraints.NotBlank;
import java.util.Map;
import lombok.Data;

@Data
public class GameCharacterRequestDto {
    private Long userId;
    @NotBlank
    private String name;
    private Long portraitId;
    private Map<String, Object> data;
    private Map<String, Object> paramMap;
}
