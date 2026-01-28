import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

public class Event extends Task {
    protected LocalDate from;
    protected LocalDate to;
    private static final DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter.ofPattern("MMM dd yyyy");
    private static final ArrayList<DateTimeFormatter> INPUT_FORMATS = new ArrayList<>();

    static {
        INPUT_FORMATS.add(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        INPUT_FORMATS.add(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        INPUT_FORMATS.add(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
    }

    public Event(String description, LocalDate from, LocalDate to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    /**
     * Creates an Event task from a command string.
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
        
        LocalDate fromDate = parseDate(parts[1].trim());
        LocalDate toDate = parseDate(parts[2].trim());
        if (fromDate == null || toDate == null) {
            throw new IllegalArgumentException("OOPS!!! Please provide dates in one of these formats: yyyy-MM-dd, dd/MM/yyyy, or MM/dd/yyyy");
        }
        return new Event(parts[0].trim(), fromDate, toDate);
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
        return "[E]" + super.toString() + " (from: " + from.format(OUTPUT_FORMAT) + ", to: " + to.format(OUTPUT_FORMAT) + ")";
    }

    @Override
    public String toFileFormat() {
        return "E | " + (isDone ? "1" : "0") + " | " + description + " | " + from.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + " | " + to.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
}
