package tk.kirlian.SignTraderWithDucks.errors;

/**
 * Thrown to indicate a chest is protected and cannot be accessed.
 */
public class ChestProtectionException extends SignTraderException {
    public ChestProtectionException() {
        super();
    }
    public ChestProtectionException(String message) {
        super(message);
    }
    public ChestProtectionException(String message, Throwable cause) {
        super(message, cause);
    }
    public ChestProtectionException(Throwable cause) {
        super(cause);
    }
}
