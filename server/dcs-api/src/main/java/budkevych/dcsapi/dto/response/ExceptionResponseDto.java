package budkevych.dcsapi.dto.response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ExceptionResponseDto {
    private String exception;
    private String message;
    private String stackTrace;
}
