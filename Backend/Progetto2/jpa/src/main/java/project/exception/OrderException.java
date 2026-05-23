package project.exception;

public class OrderException extends BusinessException {

    public OrderException() {

        super(
                "ORDER_EXCEPTION",
                "Ordine non cancellabile"
        );
    }
}
