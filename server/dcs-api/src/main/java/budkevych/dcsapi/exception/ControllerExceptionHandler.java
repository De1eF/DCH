package budkevych.dcsapi.exception;

import budkevych.dcsapi.dto.response.ExceptionResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler({AuthenticationException.class,
            ResourceNotFoundException.class,
            AlreadyExistsException.class})
    ResponseEntity<?> handleBadRequest(Throwable exception) {
        ExceptionResponseDto exceptionResponseDto = new ExceptionResponseDto();
        exceptionResponseDto.setMessage(exception.getMessage());
        exceptionResponseDto.setException(exception.getClass().getName());
        return ResponseEntity.badRequest().body(exceptionResponseDto);
    }

    @ExceptionHandler(NoAccessException.class)
    ResponseEntity<?> handleNotAuthorized(Throwable exception) {
        ExceptionResponseDto exceptionResponseDto = new ExceptionResponseDto();
        exceptionResponseDto.setMessage(exception.getMessage());
        exceptionResponseDto.setException(exception.getClass().getName());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(exceptionResponseDto);
    }
}
