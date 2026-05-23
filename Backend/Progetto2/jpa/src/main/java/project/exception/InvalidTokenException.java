package project.exception;

public class InvalidTokenException extends BusinessException {

    public InvalidTokenException() {

        super(
                "INVALID_TOKEN",
                "Token non valido o scaduto"
        );
    }
}