package tk.allele.util.commands;

import org.bukkit.command.CommandSender;
import tk.allele.permissions.PermissionsException;

import java.util.*;

/**
 * Keeps track of commands.
 * <p>
 * This doesn't implement {@link org.bukkit.command.CommandMap}, as that was
 * designed for handling top-level commands, not sub-commands such as
 * <kbd>/duckshop link</kbd>.
 * <p>
 * <strong>Note:</strong> this class is not thread-safe.
 */
public class CommandDispatcher extends Command implements org.bukkit.command.CommandExecutor {
    final Map<String, Command> commandMap;

    public CommandDispatcher(String label) {
        super(label);
        commandMap = new HashMap<String, Command>();
    }

    public void registerCommand(Command command) {
        if(!commandMap.containsKey(command.getName())) {
            commandMap.put(command.getName(), command);
        } else {
            throw new IllegalStateException("command name " + command.getName() + " already taken");
        }
    }

    @Override
    public void execute(CommandSender sender, CommandContext context) throws CommandException, PermissionsException {
        Command command = commandMap.get(context.getArguments().get(0));
        if(command != null) {
            command.execute(sender, context.shift(1));
        } else {
            throw new CommandException(this, context);
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        try {
            // CommandContext constructor expects the label to be the first
            // element of the list
            ArrayList<String> input = new ArrayList<String>(1 + args.length);
            input.add(label);
            input.addAll(Arrays.asList(args));

            // Now execute the command
            execute(sender, new CommandContext(input));
        } catch (CommandException e) {
            sender.sendMessage(e.toString());
        } catch (PermissionsException e) {
            sender.sendMessage("I'm sorry, " + sender.getName() + ". I'm afraid I can't do that.");
        }
        return true;
    }
}
