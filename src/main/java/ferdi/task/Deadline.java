package ferdi.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents a task with a due date.
 */
public class Deadline extends Task {
    protected LocalDate by;
    private static final DateTimeFormatter INPUT_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter.ofPattern("MMM dd yyyy");

    /**
     * Creates a deadline with description and due date.
     *
     * @param description task description
     * @param by due date as LocalDate
     */
    public Deadline(String description, LocalDate by) {
        super(description);
        this.by = by;
    }

    /**
     * Creates a Deadline task from a command string.
     *
     * @param commandArgs user-provided arguments after the command word
     * @return parsed deadline task
     * @throws IllegalArgumentException if description or /by component is missing
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
        String description = parts[0].trim();
        String dateStr = parts[1].trim();
        
        try {
            LocalDate date = LocalDate.parse(dateStr, INPUT_FORMAT);
            return new Deadline(description, date);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("OOPS!!! Please use yyyy-MM-dd format for the date (e.g., 2019-12-02).");
        }
    }

    /**
     * Updates a Deadline task from a command string.
     *
     * @param updateArgs user-provided update arguments
     * @throws IllegalArgumentException if format is invalid
     */
    public void updateFromCommand(String updateArgs) throws IllegalArgumentException {
        if (!updateArgs.contains(" /by ")) {
            throw new IllegalArgumentException("OOPS!!! For deadlines, use /by to update the due date (e.g., update 1 /by 2024-12-31).");
        }

        String[] parts = updateArgs.split(" /by ", 2);
        if (parts.length < 2 || parts[1].trim().isEmpty()) {
            throw new IllegalArgumentException("OOPS!!! Please provide a new date after /by.");
        }

        try {
            LocalDate newDate = LocalDate.parse(parts[1].trim(), INPUT_FORMAT);
            this.by = newDate;
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("OOPS!!! Please use yyyy-MM-dd format for the date (e.g., 2024-12-31).");
        }
    }

    /**
     * Updates the due date of this deadline.
     *
     * @param newBy new due date
     */
    public void setBy(LocalDate newBy) {
        this.by = newBy;
    }

    /**
     * Returns the due date of this deadline.
     *
     * @return due date
     */
    public LocalDate getBy() {
        return this.by;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by.format(OUTPUT_FORMAT) + ")";
    }

    @Override
    public String toFileFormat() {
        return "D | " + (isDone ? "1" : "0") + " | " + description + " | " + by;
    }
}

