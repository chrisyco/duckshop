package tk.allele.util.commands;

import java.util.Arrays;
import java.util.List;

/**
 * Represents the arguments that are passed to a command.
 */
public class CommandContext {
    protected final List<String> input;
    protected final int index;
    protected final String label;
    protected final List<String> args;

    public CommandContext(List<String> input, int index) {
        this.input = input;
        this.index = index;
        this.label = input.get(index);
        this.args = input.subList(index + 1, input.size());
    }

    public CommandContext(List<String> input) {
        this(input, 0);
    }

    public CommandContext(String input) {
        this(Arrays.asList(input.split(" ")));
    }

    public String getLabel() {
        return label;
    }

    public List<String> getArguments() {
        return args;
    }

    public CommandContext shift(int offset) {
        return new CommandContext(input, index + offset);
    }
}
