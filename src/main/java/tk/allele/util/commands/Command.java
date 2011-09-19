package tk.allele.util.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import tk.allele.permissions.PermissionsException;
import tk.allele.permissions.PermissionsManager;

import java.util.List;

/**
 * A custom command class that uses Allele's permissions manager.
 */
public abstract class Command {
    final String name;
    protected String description;

    protected Command(String name, String description) {
        this.name = name;
        this.description = description;
    }

    protected Command(String name) {
        this(name, "");
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public abstract void execute(CommandSender sender, CommandContext context) throws CommandException, PermissionsException;
}
