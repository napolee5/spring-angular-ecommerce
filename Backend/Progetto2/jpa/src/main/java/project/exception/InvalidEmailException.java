package project.exception;

public class InvalidEmailException extends BusinessException {

    public InvalidEmailException(String message) {

        super(
                "INVALID_EMAIL",
                message
        );
    }
}