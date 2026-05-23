package project.exception;


public class InvalidPasswordException extends BusinessException {

    public InvalidPasswordException() {

        super(
                "INVALID_PASSWORD",
                "La password deve contenere 8 carattere e almeno 2 numeri"
        );
    }
}