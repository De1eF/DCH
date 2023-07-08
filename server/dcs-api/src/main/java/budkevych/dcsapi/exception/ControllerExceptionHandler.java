package budkevych.dcsapi.exception;

import budkevych.dcsapi.dto.response.ExceptionResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler({ResourceNotFoundException.class,
            AlreadyExistsException.class,
            InvalidJwtAuthenticationException.class,
            MethodArgumentNotValidException.class})
    ResponseEntity<?> handleBadRequest(Throwable exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ExceptionResponseDto
                        .builder()
                        .exception(exception.getClass().getName())
                        .message(exception.getMessage() + " cause: " + exception.getCause())
                        .build());
    }

    @ExceptionHandler({NoAccessException.class,
            AuthenticationException.class})
    ResponseEntity<?> handleForbidden(Throwable exception) {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(ExceptionResponseDto
                        .builder()
                        .exception(exception.getClass().getName())
                        .message(exception.getMessage())
                        .build());
    }
}
