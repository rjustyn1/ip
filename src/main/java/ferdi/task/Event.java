package ferdi.task;

/**
 * Represents an event with a start and end time.
 */
public class Event extends Task {
    protected String from;
    protected String to;

    /**
     * Creates an event with description, start time, and end time.
     *
     * @param description task description
     * @param from start time text
     * @param to end time text
     */
    public Event(String description, String from, String to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    /**
     * Creates an Event task from a command string.
     *
     * @param commandArgs user-provided arguments after the command word
     * @return parsed event task
     * @throws IllegalArgumentException if description, /from, or /to components are missing
     */
    public static Event createFromCommand(String commandArgs) throws IllegalArgumentException {
        if (commandArgs == null || commandArgs.trim().isEmpty()) {
            throw new IllegalArgumentException("OOPS!!! The description of an event cannot be empty.");
        }
        if (!commandArgs.contains(" /from ") || !commandArgs.contains(" /to ")) {
            throw new IllegalArgumentException("OOPS!!! Please specify event duration using both /from and /to.");
        }
        String[] parts = commandArgs.split(" /from | /to ");
        if (parts.length != 3 || parts[0].trim().isEmpty() || parts[1].trim().isEmpty() || parts[2].trim().isEmpty()) {
            throw new IllegalArgumentException("OOPS!!! Event description, start time, and end time are all required.");
        }
        return new Event(parts[0].trim(), parts[1].trim(), parts[2].trim());
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + from + ", to: " + to + ")";
    }

    @Override
    public String toFileFormat() {
        return "E | " + (isDone ? "1" : "0") + " | " + description + " | " + from + " | " + to;
    }
}
