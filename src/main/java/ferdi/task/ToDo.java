package ferdi.task;

public class ToDo extends Task {
    public ToDo(String description) {
        super(description);
    }

    /**
     * Creates a ToDo task from a command string.
     */
    public static ToDo createFromCommand(String commandArgs) throws IllegalArgumentException {
        if (commandArgs == null || commandArgs.trim().isEmpty()) {
            throw new IllegalArgumentException("OOPS!!! The description of a todo cannot be empty.");
        }
        return new ToDo(commandArgs.trim());
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

    @Override
    public String toFileFormat() {
        return "T | " + (isDone ? "1" : "0") + " | " + description;
    }
}
