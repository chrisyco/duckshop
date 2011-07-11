package tk.kirlian.DuckShop.errors;

/**
 * Thrown to indicate someone doesn't have permission to do something.
 */
public class PermissionsException extends DuckShopException {
    public PermissionsException() {
        super();
    }
    public PermissionsException(String message) {
        super(message);
    }
}
