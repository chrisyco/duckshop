package tk.allele.util.commands;

/**
 * Thrown to indicate a malformed command.
 */
public class CommandException extends Exception {
    Command command;

    public CommandException(Command command, CommandContext context) {
        super();
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }

    public String getMessage() {
        return command.getName();
    }
}
