package project.exception;

public class ProductNotFoundException extends BusinessException {

    public ProductNotFoundException() {

        super(
                "PRODUCT_NOT_FOUND",
                "Prodotto non trovato"
        );
    }
}