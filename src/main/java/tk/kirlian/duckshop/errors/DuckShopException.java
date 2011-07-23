package tk.kirlian.duckshop.errors;

/**
 * An exception somehow relating to DuckShop.
 */
public class DuckShopException extends Exception {
    public DuckShopException() {
        super();
    }
    public DuckShopException(String message) {
        super(message);
    }
}
