package ferdi.task;

/**
 * Represents a task with a description and completion status.
 */
public abstract class Task {
    protected final String description;
    protected boolean isDone;

    /**
     * Creates a new task with the given description.
     * The task is initially marked as not done.
     *
     * @param description text describing the task
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Returns the status icon for this task.
     *
     * @return "X" when done, or space when not done
     */
    public String getStatusIcon() {
        return (isDone ? "X" : " ");
    }

    /**
     * Marks task as done.
     */
    public void mark() {
        this.isDone = true;
    }

    /**
     * Marks task as not done.
     */
    public void unmark() {
        this.isDone = false;
    }

    /**
     * Returns a string representation of this task.
     * For printing purposes.
     *
     * @return display-friendly text including status and description
     */
    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }

    /**
     * Returns the file format representation of this task.
     * To be overridden by subclasses.
     *
     * @return pipe-delimited representation for persistence
     */
    public abstract String toFileFormat();
}
