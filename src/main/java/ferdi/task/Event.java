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

    /**
     * Updates an Event task from a command string.
     *
     * @param updateArgs user-provided update arguments
     * @throws IllegalArgumentException if format is invalid
     */
    public void updateFromCommand(String updateArgs) throws IllegalArgumentException {
        boolean hasFrom = updateArgs.contains(" /from ");
        boolean hasTo = updateArgs.contains(" /to ");

        if (!hasFrom && !hasTo) {
            throw new IllegalArgumentException("OOPS!!! For events, use /from to update start time or /to to update end time.");
        }

        if (hasFrom && hasTo) {
            String[] parts = updateArgs.split(" /from | /to ");
            if (parts.length < 3) {
                throw new IllegalArgumentException("OOPS!!! Please provide valid values for /from and /to.");
            }
            String newFrom = parts[1].trim();
            String newTo = parts[2].trim();
            if (newFrom.isEmpty() || newTo.isEmpty()) {
                throw new IllegalArgumentException("OOPS!!! Start time and end time cannot be empty.");
            }
            this.from = newFrom;
            this.to = newTo;
        } else if (hasFrom) {
            String[] parts = updateArgs.split(" /from ", 2);
            if (parts.length < 2 || parts[1].trim().isEmpty()) {
                throw new IllegalArgumentException("OOPS!!! Please provide a new start time after /from.");
            }
            this.from = parts[1].trim();
        } else {
            String[] parts = updateArgs.split(" /to ", 2);
            if (parts.length < 2 || parts[1].trim().isEmpty()) {
                throw new IllegalArgumentException("OOPS!!! Please provide a new end time after /to.");
            }
            this.to = parts[1].trim();
        }
    }

    /**
     * Updates the start time of this event.
     *
     * @param newFrom new start time
     */
    public void setFrom(String newFrom) {
        this.from = newFrom;
    }

    /**
     * Updates the end time of this event.
     *
     * @param newTo new end time
     */
    public void setTo(String newTo) {
        this.to = newTo;
    }

    /**
     * Returns the start time of this event.
     *
     * @return start time
     */
    public String getFrom() {
        return this.from;
    }

    /**
     * Returns the end time of this event.
     *
     * @return end time
     */
    public String getTo() {
        return this.to;
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
