package tk.allele.duckshop.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import tk.allele.duckshop.LinkState;
import tk.allele.permissions.PermissionsException;
import tk.allele.util.commands.Command;
import tk.allele.util.commands.CommandContext;
import tk.allele.util.commands.CommandException;

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
    public void execute(CommandSender sender, CommandContext context) throws CommandException, PermissionsException {
        if(sender instanceof Player) {
            linkState.startLink((Player) sender);
            sender.sendMessage("Left click on a sign to link it.");
        } else {
            sender.sendMessage("Only players can link chests.");
        }
    }
}
