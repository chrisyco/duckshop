package tk.kirlian.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.SimpleFormatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import tk.kirlian.util.StringTools;

/**
 * A custom log formatter that adds a prefix.
 */
public class PrefixFormatter extends Formatter {
    private String prefix;
    private SimpleDateFormat dateFormat;

    /**
     * Creates a new PrefixFormatter instance.
     *
     * @param prefix The prefix to use.
     */
    public PrefixFormatter(String prefix) {
        this.prefix = prefix;
        this.dateFormat = new SimpleDateFormat("HH:mm:ss");
    }

    public String format(LogRecord rec) {
        StringBuilder buf = new StringBuilder(128);
        buf.append(dateFormat.format(new Date(rec.getMillis())));
        buf.append(" [");
        buf.append(rec.getLevel().getName());
        buf.append("] [");
        buf.append(prefix);
        buf.append("] ");
        buf.append(formatMessage(rec));
        return buf.toString();
    }
}
