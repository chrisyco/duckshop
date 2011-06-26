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
}
