package budkevych.dcsapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class NoAccessException extends RuntimeException {
    public NoAccessException(String message) {
        super(message);
    }
}
