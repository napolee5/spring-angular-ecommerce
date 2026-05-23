package project.exception;

public class NameErrorException extends BusinessException {

    public NameErrorException() {

        super(
                "INVALID_NAME",
                 "Il nome non può contenere caratteri speciali o numeri"
        );
    }
}