package project.exception;

public class CartNotFoundException extends BusinessException {

    public CartNotFoundException() {

        super(
                "CART_NOT_FOUND",
                "Carrello non trovato"
        );
    }
}