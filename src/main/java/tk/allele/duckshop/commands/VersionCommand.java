package tk.allele.duckshop.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginDescriptionFile;
import tk.allele.duckshop.DuckShop;
import tk.allele.permissions.PermissionsException;
import tk.allele.permissions.PermissionsManager;
import tk.allele.util.StringTools;
import tk.allele.util.commands.Command;
import tk.allele.util.commands.CommandContext;

/**
 * Implements the DuckShop version command.
 */
public class VersionCommand extends Command {
    final PermissionsManager permissionsManager;
    final PluginDescriptionFile pdf;

    public VersionCommand(DuckShop plugin) {
        super("version");
        this.permissionsManager = plugin.permissions;
        this.pdf = plugin.getDescription();
        setDescription("Displays the version of " + pdf.getName());
    }

    @Override
    public void execute(CommandSender sender, CommandContext context)
            throws PermissionsException {
        sender.sendMessage(pdf.getName() + " version " + pdf.getVersion() + " (" + StringTools.join(pdf.getAuthors(), ", ") + ")");
    }
}
