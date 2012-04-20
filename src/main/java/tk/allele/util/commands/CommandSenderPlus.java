package tk.allele.util.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * A wrapper around CommandSender that adds colors automatically.
 */
public class CommandSenderPlus {
    final CommandSender commandSender;

    /**
     * Create a CommandSenderPlus by wrapping an existing CommandSender.
     */
    public CommandSenderPlus(CommandSender commandSender) {
        this.commandSender = commandSender;
    }

    /**
     * Retrieve the original CommandSender object.
     */
    public CommandSender get() {
        return commandSender;
    }

    /**
     * Determine whether this represents a player or the server console.
     */
    public boolean isPlayer() {
        return commandSender instanceof Player;
    }

    /**
     * Send an informational message, e.g. "42 ducks retrieved".
     */
    public void info(String... message) {
        for (String msg : message) {
            commandSender.sendMessage(ChatColor.BLUE + msg);
        }
    }

    /**
     * Send a message telling the user to do something, e.g. "click on the sign".
     */
    public void action(String... message) {
        for (String msg : message) {
            commandSender.sendMessage(ChatColor.GREEN + msg);
        }
    }

    /**
     * Send a message saying something went wrong, e.g. "divided by zero"
     */
    public void error(String... message) {
        for (String msg : message) {
            commandSender.sendMessage(ChatColor.RED + msg);
        }
    }

    /**
     * Warn the user about something, e.g. "this sign may explode; please don't touch it"
     */
    public void warning(String... message) {
        for (String msg : message) {
            commandSender.sendMessage(ChatColor.YELLOW + msg);
        }
    }
}
