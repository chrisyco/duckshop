package tk.kirlian.util;

import java.util.regex.Pattern;
import java.util.regex.Matcher;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;

/**
 * Random functions that don't fit anywhere else.
 */
public class Misc {
    private static final String integerRegex = "([+-]?\\d+)";
    private static final Pattern locationRegex = Pattern.compile(
        "\\s*" + "((\\w+)\\s*:)?" +
        "\\s*" + integerRegex + "\\s*" + "," +
        "\\s*" + integerRegex + "\\s*" + "," +
        "\\s*" + integerRegex + "\\s*");
    /**
     * Try to parse a {@link Location} object in the form "WorldName:X,Y,Z".
     * @return A Location object, or null.
     */
    public static Location parseLocation(final Server server, final World defaultWorld, final String locationString) {
        Matcher matcher = locationRegex.matcher(locationString);
        if(matcher.matches()) {
            World world = null;
            if(matcher.group(1) != null) {
                world = server.getWorld(matcher.group(2));
            }
            if(world == null) {
                world = defaultWorld;
            }
            int x = Integer.parseInt(matcher.group(3));
            int y = Integer.parseInt(matcher.group(4));
            int z = Integer.parseInt(matcher.group(5));
            return new Location(world, x, y, z);
        } else {
            return null;
        }
    }

    /**
     * Convert a {@link Location} object to a string that can be parsed
     * by {@link #parseLocation(Server, World, String)}.
     */
    public static String locationToString(Location location) {
        StringBuilder s = new StringBuilder(32);
        if(location.getWorld() != null) {
            s.append(location.getWorld().getName());
            s.append(":");
        }
        s.append(Integer.toString(location.getBlockX()));
        s.append(",");
        s.append(Integer.toString(location.getBlockY()));
        s.append(",");
        s.append(Integer.toString(location.getBlockZ()));
        return s.toString();
    }
}
