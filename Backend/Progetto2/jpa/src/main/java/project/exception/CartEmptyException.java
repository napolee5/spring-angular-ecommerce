package project.exception;

public class CartEmptyException extends BusinessException {

    public CartEmptyException() {

        super(
                "CART_EMPTY",
                "Il carrello è vuoto"
        );
    }
}