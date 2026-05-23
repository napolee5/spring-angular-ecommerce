package project.exception;

public class NameProductException extends BusinessException {

    public NameProductException() {

        super(
                "NAME_ALREADY_USE",
                "Nome già in uso"
        );
    }

}