import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

public class Deadline extends Task {
    protected LocalDate by;
    private static final DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter.ofPattern("MMM dd yyyy");
    private static final ArrayList<DateTimeFormatter> INPUT_FORMATS = new ArrayList<>();

    static {
        INPUT_FORMATS.add(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        INPUT_FORMATS.add(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        INPUT_FORMATS.add(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
    }

    public Deadline(String description, LocalDate by) {
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
        
        LocalDate date = parseDate(parts[1].trim());
        if (date == null) {
            throw new IllegalArgumentException("OOPS!!! Please provide date in one of these formats: yyyy-MM-dd, dd/MM/yyyy, or MM/dd/yyyy");
        }
        return new Deadline(parts[0].trim(), date);
    }

    private static LocalDate parseDate(String dateStr) {
        for (DateTimeFormatter formatter : INPUT_FORMATS) {
            try {
                return LocalDate.parse(dateStr, formatter);
            } catch (DateTimeParseException e) {
                // Try next format
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by.format(OUTPUT_FORMAT) + ")";
    }

    @Override
    public String toFileFormat() {
        return "D | " + (isDone ? "1" : "0") + " | " + description + " | " + by.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
}
