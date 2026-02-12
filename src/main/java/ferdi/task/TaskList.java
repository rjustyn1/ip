package ferdi.task;

import java.util.ArrayList;

/**
 * Manages the in-memory list of tasks.
 */
public class TaskList {
    private final ArrayList<Task> tasks;

    /**
     * Creates an empty task list.
     */
    public TaskList() {
        this.tasks = new ArrayList<Task>();
    }

    /**
     * Creates a task list backed by an existing collection.
     *
     * @param tasks initial tasks to manage
     */
    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Returns the number of tasks stored.
     *
     * @return count of tasks
     */
    public int size() {
        return tasks.size();
    }

    /**
     * Retrieves a task by index.
     *
     * @param index zero-based position
     * @return task at the requested position
     */
    public Task getTask(int index) {
        assert index >= 0 && index < tasks.size() : "Index out of bounds: " + index;
        return tasks.get(index);
    }

    /**
     * Returns a copy of the current task list.
     *
     * @return defensive copy of tasks
     */
    public ArrayList<Task> getTasks() {
        return new ArrayList<>(tasks);
    }

    /**
     * Appends a new task.
     *
     * @param task task to add
     */
    public void addTask(Task task) {
        assert task != null : "Cannot add null task";
        tasks.add(task);
        assert tasks.contains(task) : "Task should be in list after adding";
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
        assert index >= 0 && index < tasks.size() : "Index out of bounds: " + index;
        tasks.get(index).mark();
        assert tasks.get(index).isDone() : "Task should be marked as done";
    }

    /**
     * Marks the task at the given index as not done.
     *
     * @param index zero-based task position
     */
    public void unmarkTask(int index) {
        assert index >= 0 && index < tasks.size() : "Index out of bounds: " + index;
        tasks.get(index).unmark();
        assert !tasks.get(index).isDone() : "Task should be unmarked";
    }
}
