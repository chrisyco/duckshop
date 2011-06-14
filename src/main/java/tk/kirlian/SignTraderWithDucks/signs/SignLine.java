package tk.kirlian.SignTraderWithDucks.signs;

import java.io.Serializable;
import tk.kirlian.SignTraderWithDucks.errors.*;

public class SignLine implements Serializable {
    private SignVerb verb;
    private SignItem item;
    private String lineString;

    public SignLine(SignVerb verb, SignItem item, String lineString) {
        this.verb = verb;
        this.item = item;
        this.lineString = lineString;
    }

    public static SignLine fromString(String lineString) throws InvalidSyntaxException {
        String[] bits = lineString.split("\\s+", 2);
        SignVerb verb = SignVerb.fromString(bits[0]);
        SignItem item = SignItem.fromString(bits[1]);
        return new SignLine(verb, item, lineString);
    }

    /**
     * Retrieve the original item definition as a String.
     */
    public String getOriginalString() {
        return lineString;
    }

    public String toString() {
        return verb + " " + item;
    }

    public SignVerb getVerb() {
        return verb;
    }

    public SignItem getItem() {
        return item;
    }
}
