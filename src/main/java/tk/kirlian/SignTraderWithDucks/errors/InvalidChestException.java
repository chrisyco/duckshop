package tk.kirlian.SignTraderWithDucks.errors;

/**
 * Thrown to indicate a block should be a chest, but isn't.
 */
public class InvalidChestException extends SignTraderException {
    public InvalidChestException() {
        super();
    }
    public InvalidChestException(String message) {
        super(message);
    }
    public InvalidChestException(String message, Throwable cause) {
        super(message, cause);
    }
    public InvalidChestException(Throwable cause) {
        super(cause);
    }
}
