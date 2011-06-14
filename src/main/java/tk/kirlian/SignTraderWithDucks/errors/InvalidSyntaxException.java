package tk.kirlian.SignTraderWithDucks.errors;

/**
 * Thrown when a string cannot be parsed, usually in a TradingSign.
 */
public class InvalidSyntaxException extends SignTraderException {
    public InvalidSyntaxException() {
        super();
    }
    public InvalidSyntaxException(String message) {
        super(message);
    }
    public InvalidSyntaxException(String message, Throwable cause) {
        super(message, cause);
    }
    public InvalidSyntaxException(Throwable cause) {
        super(cause);
    }
}
