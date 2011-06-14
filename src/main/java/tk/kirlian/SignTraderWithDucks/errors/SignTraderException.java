package tk.kirlian.SignTraderWithDucks.errors;

/**
 * An exception somehow relating to SignTraderWithDucks.
 */
public class SignTraderException extends Exception {
    public SignTraderException() {
        super();
    }
    public SignTraderException(String message) {
        super(message);
    }
    public SignTraderException(String message, Throwable cause) {
        super(message, cause);
    }
    public SignTraderException(Throwable cause) {
        super(cause);
    }
}
