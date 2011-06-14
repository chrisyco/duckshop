package tk.kirlian.SignTraderWithDucks.signs;

import tk.kirlian.util.StringTools;
import tk.kirlian.SignTraderWithDucks.errors.*;

/**
 * A verb: either "Give" or "Take".
 */
public enum SignVerb {
    Give,
    Take;

    public static SignVerb fromString(String name) throws InvalidSyntaxException {
        try {
            return SignVerb.valueOf(StringTools.capitalizeFirstLetter(name));
        } catch(IllegalArgumentException ex) {
            throw new InvalidSyntaxException(ex);
        }
    }
}
