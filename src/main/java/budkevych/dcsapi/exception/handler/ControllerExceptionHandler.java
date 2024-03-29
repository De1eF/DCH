package budkevych.dcsapi.exception.handler;

import budkevych.dcsapi.dto.response.ExceptionResponseDto;
import budkevych.dcsapi.exception.AlreadyExistsException;
import budkevych.dcsapi.exception.AuthenticationException;
import budkevych.dcsapi.exception.InvalidJwtAuthenticationException;
import budkevych.dcsapi.exception.NoAccessException;
import budkevych.dcsapi.exception.ResourceNotFoundException;
import java.util.Arrays;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler({AlreadyExistsException.class,
            InvalidJwtAuthenticationException.class,
            MethodArgumentNotValidException.class})
    ResponseEntity<?> handleBadRequest(Throwable exception) {
        System.out.println(exception.getMessage()
                + System.lineSeparator()
                + Arrays.toString(exception.getStackTrace()));
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ExceptionResponseDto
                        .builder()
                        .exception(exception.getClass().getName())
                        .message(exception.getMessage())
                        .stackTrace(Arrays.toString(exception.getStackTrace()))
                        .build());
    }

    @ExceptionHandler({ResourceNotFoundException.class})
    ResponseEntity<?> handleNotFound(Throwable exception) {
        System.out.println(exception.getMessage()
                + System.lineSeparator()
                + Arrays.toString(exception.getStackTrace()));
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ExceptionResponseDto
                        .builder()
                        .exception(exception.getClass().getName())
                        .message(exception.getMessage())
                        .stackTrace(Arrays.toString(exception.getStackTrace()))
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
                        .stackTrace(Arrays.toString(exception.getStackTrace()))
                        .build());
    }
}
