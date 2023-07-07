package budkevych.dcsapi.dto.response;

import lombok.Data;

@Data
public class ExceptionResponseDto {
    private String exception;
    private String message;
}
