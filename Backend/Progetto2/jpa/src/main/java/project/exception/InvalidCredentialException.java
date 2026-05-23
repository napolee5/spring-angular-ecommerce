package project.exception;

public class InvalidCredentialException extends BusinessException {

    public InvalidCredentialException() {
        super("INVALID_CREDENTIALS",
                "Email o password errati");
    }
}