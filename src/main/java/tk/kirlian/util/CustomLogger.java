package tk.kirlian.util;

import java.util.Hashtable;
import java.util.logging.Logger;
import java.util.logging.LogRecord;

/**
 * A custom logger that works around the limitations of Minecraft's
 * logging system by overriding a few things.
 */
public class CustomLogger extends Logger {
    final private static String CUSTOM_LOGGER_SUFFIX = "._CustomLogger";
    private static Hashtable<String, CustomLogger> loggers = new Hashtable<String, CustomLogger>();
    protected Logger baseLogger;
    protected String name;

    protected CustomLogger(String name, String resourceBundleName) {
        super(name + CUSTOM_LOGGER_SUFFIX, resourceBundleName);
        this.baseLogger = Logger.getLogger(name, resourceBundleName);
        this.name = getName().replace(CUSTOM_LOGGER_SUFFIX, "");
    }

    public static CustomLogger getLogger(String name) {
        CustomLogger logger = loggers.get(name);
        if(logger == null) {
            logger = new CustomLogger(name, null);
            loggers.put(name, logger);
        }
        return logger;
    }

    public void log(LogRecord record) {
        record.setMessage("[" + name + "] " + record.getMessage());
        baseLogger.log(record);
    }
}
