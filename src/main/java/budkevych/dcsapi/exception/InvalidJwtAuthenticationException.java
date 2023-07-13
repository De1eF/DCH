package budkevych.dcsapi.exception;

public class InvalidJwtAuthenticationException extends Exception {
    public InvalidJwtAuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }
}
