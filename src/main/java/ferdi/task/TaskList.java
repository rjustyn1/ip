package ferdi.task;

import java.util.ArrayList;

/**
 * Manages the in-memory list of tasks.
 */
public class TaskList {
    private final ArrayList<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<Task>();
    }

    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public int size() {
        return tasks.size();
    }

    public Task getTask(int index) {
        return tasks.get(index);
    }

    public ArrayList<Task> getTasks() {
        return new ArrayList<>(tasks);
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    /**
     * Returns tasks whose descriptions contain the given keyword (case-insensitive).
     *
     * @param keyword search term to match against task descriptions
     * @return tasks whose descriptions contain the keyword
     */
    public ArrayList<Task> findTasks(String keyword) {
        ArrayList<Task> matches = new ArrayList<>();
        if (keyword == null || keyword.isEmpty()) {
            return matches;
        }
        String loweredKeyword = keyword.toLowerCase();
        for (Task task : tasks) {
            if (task.getDescription().toLowerCase().contains(loweredKeyword)) {
                matches.add(task);
            }
        }
        return matches;
    }

    public void markTask(int index) {
        tasks.get(index).mark();
    }

    public void unmarkTask(int index) {
        tasks.get(index).unmark();
    }
}
