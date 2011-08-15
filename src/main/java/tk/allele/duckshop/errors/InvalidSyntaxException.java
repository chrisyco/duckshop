package tk.allele.duckshop.errors;

/**
 * Thrown when a string cannot be parsed, usually in a TradingSign.
 */
public class InvalidSyntaxException extends DuckShopException {
    public InvalidSyntaxException() {
        super();
    }
    public InvalidSyntaxException(String message) {
        super(message);
    }
}
