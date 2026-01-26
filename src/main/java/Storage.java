import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Handles loading tasks from and saving tasks to disk.
 */
public class Storage {
    private final String filePath;
    private final ArrayList<Task> tasks;

    /**
     * Create storage pointing to a relative path like "data/duke.txt".
     */
    public Storage(String relativePath) {
        this.filePath = relativePath;
        this.tasks = loadFromDisk();
    }

    /**
     * Return a shallow copy of tasks for read-only display.
     */
    public ArrayList<Task> getTasks() {
        return new ArrayList<>(tasks);
    }

    public int size() {
        return tasks.size();
    }

    public Task getTask(int index) {
        return tasks.get(index);
    }

    /**
     * Add a task and persist immediately.
     */
    public void addTask(Task task) {
        tasks.add(task);
        saveToDisk();
    }

    /**
     * Mark a task as done by 0-based index and persist.
     */
    public void markTask(int index) {
        tasks.get(index).mark();
        saveToDisk();
    }

    /**
     * Unmark a task by 0-based index and persist.
     */
    public void unmarkTask(int index) {
        tasks.get(index).unmark();
        saveToDisk();
    }

    private ArrayList<Task> loadFromDisk() {
        ArrayList<Task> loaded = new ArrayList<>();
        File file = new File(filePath);
        ensureParentDirectoryExists(file);
        if (!file.exists()) {
            return loaded;
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
        return loaded;
    }

    /**
     * Save current tasks to disk. Creates folders/files as needed.
     */
    private void saveToDisk() {
        File file = new File(filePath);
        ensureParentDirectoryExists(file);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (Task task : tasks) {
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
                task = new Deadline(description, parts[3]);
            }
        } else if (type.equals("E")) {
            if (parts.length >= 5) {
                task = new Event(description, parts[3], parts[4]);
            }
        }

        if (task != null && isDone) {
            task.mark();
        }
        return task;
    }
}