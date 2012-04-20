package tk.allele.duckshop.commands;

import org.bukkit.entity.Player;
import tk.allele.duckshop.listeners.LinkState;
import tk.allele.permissions.PermissionsException;
import tk.allele.util.commands.Command;
import tk.allele.util.commands.CommandContext;
import tk.allele.util.commands.CommandException;
import tk.allele.util.commands.CommandSenderPlus;

/**
 * The command for linking chests.
 */
public class LinkCommand extends Command {
    final LinkState linkState;

    public LinkCommand(LinkState linkState) {
        super("link", "Link a sign to a chest");
        this.linkState = linkState;
    }

    @Override
    public void execute(CommandSenderPlus sender, CommandContext context) throws CommandException, PermissionsException {
        if (sender.isPlayer()) {
            linkState.startLink((Player) sender.get());
            sender.action("Left click on a sign to link it.");
        } else {
            sender.error("Only players can link chests.");
        }
    }
}
