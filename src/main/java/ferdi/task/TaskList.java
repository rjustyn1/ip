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
        tasks.add(task);
    }

    /**
     * Marks the task at the given index as done.
     *
     * @param index zero-based task position
     */
    public void markTask(int index) {
        tasks.get(index).mark();
    }

    /**
     * Marks the task at the given index as not done.
     *
     * @param index zero-based task position
     */
    public void unmarkTask(int index) {
        tasks.get(index).unmark();
    }
}
