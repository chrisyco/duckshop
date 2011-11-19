package tk.allele.duckshop.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import tk.allele.duckshop.listeners.LinkState;
import tk.allele.permissions.PermissionsException;
import tk.allele.util.commands.Command;
import tk.allele.util.commands.CommandContext;
import tk.allele.util.commands.CommandException;

/**
 * Cancel a linking operation in progress.
 */
public class CancelCommand extends Command {
    final LinkState linkState;

    public CancelCommand(LinkState linkState) {
        super("cancel", "Cancel a linking operation in progress");
        this.linkState = linkState;
    }

    @Override
    public void execute(CommandSender sender, CommandContext context) throws CommandException, PermissionsException {
        if (sender instanceof Player) {
            linkState.cancelLink((Player) sender);
            sender.sendMessage("Linking cancelled.");
        } else {
            sender.sendMessage("Only players can use this command.");
        }
    }
}
