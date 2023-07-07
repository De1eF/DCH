package budkevych.dcsapi.exception;

import budkevych.dcsapi.dto.response.ActionResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler({AuthenticationException.class,
            ResourceNotFoundException.class,
            AlreadyExistsException.class,
            InvalidJwtAuthenticationException.class})
    ResponseEntity<?> handleBadRequest(Throwable exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ActionResponseDto
                        .builder()
                        .message("Bad request, An exception has occurred, "
                                + exception.getClass().getName()
                                + " has been thrown with message "
                                + exception.getMessage())
                        .build());
    }

    @ExceptionHandler(NoAccessException.class)
    ResponseEntity<?> handleNotAuthorized(Throwable exception) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(ActionResponseDto
                        .builder()
                        .message("Not authorized, An exception has occurred, "
                                + exception.getClass().getName()
                                + "has been thrown with message "
                                + exception.getMessage())
                        .build());
    }
}
