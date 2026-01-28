import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

/**
 * Handles loading tasks from and saving tasks to disk.
 */
public class Storage {
    private final String filePath;
    /**
     * Create storage pointing to a relative path like "data/duke.txt".
     */
    public Storage(String relativePath) {
        this.filePath = relativePath;
    }

    /**
     * Load tasks from disk into a TaskList. Returns an empty list if none exists.
     */
    public TaskList load() {
        ArrayList<Task> loaded = new ArrayList<>();
        File file = new File(filePath);
        ensureParentDirectoryExists(file);
        if (!file.exists()) {
            return new TaskList(loaded);
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String trimmed = line.trim();
                if (trimmed.isEmpty()) {
                    continue;
                }
                Task parsed = parse(trimmed);
                if (parsed != null) {
                    loaded.add(parsed);
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading tasks: " + e.getMessage());
        }
        return new TaskList(loaded);
    }

    /**
     * Save the provided task list to disk. Creates folders/files as needed.
     */
    public void save(TaskList taskList) {
        File file = new File(filePath);
        ensureParentDirectoryExists(file);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (Task task : taskList.getTasks()) {
                writer.write(task.toFileFormat());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving tasks: " + e.getMessage());
        }
    }

    private void ensureParentDirectoryExists(File file) {
        File parent = file.getParentFile();
        if (parent != null && !parent.exists()) {
            parent.mkdirs();
        }
    }

    private LocalDate parseDate(String dateStr) {
        for (DateTimeFormatter formatter : INPUT_FORMATS) {
            try {
                return LocalDate.parse(dateStr, formatter);
            } catch (DateTimeParseException e) {
                // Try next format
            }
        }
        return null;
    }

    private Task parse(String line) {
        String[] parts = line.split(" \\| ");
        if (parts.length < 3) {
            return null;
        }

        String type = parts[0];
        boolean isDone = parts[1].trim().equals("1");
        String description = parts[2];
        Task task = null;

        if (type.equals("T")) {
            task = new ToDo(description);
        } else if (type.equals("D")) {
            if (parts.length >= 4) {
                LocalDate date = parseDate(parts[3].trim());
                if (date != null) {
                    task = new Deadline(description, date);
                } else {
                    System.out.println("Error parsing deadline date: " + parts[3]);
                    return null;
                }
            }
        } else if (type.equals("E")) {
            if (parts.length >= 5) {
                LocalDate fromDate = parseDate(parts[3].trim());
                LocalDate toDate = parseDate(parts[4].trim());
                if (fromDate != null && toDate != null) {
                    task = new Event(description, fromDate, toDate);
                } else {
                    System.out.println("Error parsing event date: " + parts[3] + " or " + parts[4]);
                    return null;
                }
            }
        }

        if (task != null && isDone) {
            task.mark();
        }
        return task;
    }
}