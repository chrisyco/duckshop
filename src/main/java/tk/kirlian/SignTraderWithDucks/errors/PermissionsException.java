package tk.kirlian.SignTraderWithDucks.errors;

/**
 * Thrown to indicate someone doesn't have permission to do something.
 */
public class PermissionsException extends SignTraderException {
    public PermissionsException() {
        super();
    }
    public PermissionsException(String message) {
        super(message);
    }
    public PermissionsException(String message, Throwable cause) {
        super(message, cause);
    }
    public PermissionsException(Throwable cause) {
        super(cause);
    }
}
