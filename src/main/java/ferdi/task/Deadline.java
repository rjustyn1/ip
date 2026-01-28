package ferdi.task;

public class Deadline extends Task {
    protected String by;

    public Deadline(String description, String by) {
        super(description);
        this.by = by;
    }

    /**
     * Creates a Deadline task from a command string.
     */
    public static Deadline createFromCommand(String commandArgs) throws IllegalArgumentException {
        if (commandArgs == null || commandArgs.trim().isEmpty()) {
            throw new IllegalArgumentException("OOPS!!! The description of a deadline cannot be empty.");
        }
        if (!commandArgs.contains(" /by ")) {
            throw new IllegalArgumentException("OOPS!!! Please specify when the deadline is due using /by.");
        }
        String[] parts = commandArgs.split(" /by ", 2);
        if (parts.length != 2 || parts[0].trim().isEmpty() || parts[1].trim().isEmpty()) {
            throw new IllegalArgumentException("OOPS!!! Description, and date are all required.");
        }
        return new Deadline(parts[0].trim(), parts[1].trim());
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by + ")";
    }

    @Override
    public String toFileFormat() {
        return "D | " + (isDone ? "1" : "0") + " | " + description + " | " + by;
    }
}
