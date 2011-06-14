package tk.kirlian.SignTraderWithDucks.errors;

/**
 * Thrown to indicate a trade could not be completed.
 */
public class CannotTradeException extends SignTraderException {
    public CannotTradeException() {
        super();
    }
    public CannotTradeException(String message) {
        super(message);
    }
    public CannotTradeException(String message, Throwable cause) {
        super(message, cause);
    }
    public CannotTradeException(Throwable cause) {
        super(cause);
    }
}
