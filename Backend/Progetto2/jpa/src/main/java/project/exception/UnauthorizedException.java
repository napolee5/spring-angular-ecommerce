package project.exception;

public class UnauthorizedException extends BusinessException {

    public UnauthorizedException() {

        super(
                "UNAUTHORIZED",
                "Utente non autenticato"
        );
    }
}