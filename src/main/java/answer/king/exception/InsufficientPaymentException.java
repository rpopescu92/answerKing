package answer.king.exception;


public class InsufficientPaymentException extends RuntimeException {

    public InsufficientPaymentException(String message) {
        super(message);
    }
}
