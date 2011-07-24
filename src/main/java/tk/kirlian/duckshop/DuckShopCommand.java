package tk.kirlian.duckshop;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.logging.Logger;

public class DuckShopCommand implements CommandExecutor {
    public static final String COMMAND_NAME = "duckshop";
    private final DuckShop plugin;
    private final Logger log;

    public DuckShopCommand(DuckShop plugin) {
        this.log = plugin.log;
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
        if(commandLabel.compareToIgnoreCase(COMMAND_NAME) == 0 && args.length >= 1) {
            Queue<String> argsList = new LinkedList<String>(Arrays.asList(args));
            String action = argsList.remove();
            if(action.compareToIgnoreCase("link") == 0) {
                if(sender instanceof Player) {
                    sender.sendMessage("Left click on a sign to link it.");
                    plugin.playerListener.playerStartedLink.put((Player)sender, Boolean.TRUE);
                } else {
                    sender.sendMessage(plugin.getDescription().getName() + ": Only players can use this command.");
                }
                return true;
            } else if(action.compareToIgnoreCase("cancel") == 0) {
                if(sender instanceof Player) {
                    plugin.playerListener.cancelLink((Player)sender);
                    sender.sendMessage("Linking cancelled.");
                } else {
                    sender.sendMessage(plugin.getDescription().getName() + ": Only players can use this command.");
                }
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}
