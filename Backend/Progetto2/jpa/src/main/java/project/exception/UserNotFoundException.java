package project.exception;

public class UserNotFoundException extends BusinessException {

    public UserNotFoundException(String email) {

        super(
                "USER_NOT_FOUND",
                "Utente con email " + email + " non trovato"
        );
    }

    public UserNotFoundException() {

        super(
                "USER_NOT_FOUND",
                "Utente non trovato"
        );
    }
}