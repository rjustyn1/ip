package ferdi.ui;

import java.util.ArrayList;

import ferdi.task.Task;

/**
 * Handles all user-facing console output for the application.
 */
public class Ui {
    private static final String LINE = "   ____________________________________________________________";
    private static final String LOGO = " ______           _ _\n" +
        "|  ____|         | (_)\n" +
        "| |__ ___ _ __ __| |_ \n" +
        "|  __/ _ \\ '__/ _` | |\n" +
        "| | |  __/ |   (_| | |\n" +
        "|_|  \\___|_|  \\__,_|_|\n";

    /**
     * Prints the greeting banner at application start.
     */
    public void greetStart() {
        System.out.println("Hello from\n" + LOGO);
        printLine();
        System.out.println();
        System.out.println("   Hello! I think I my name is Ferdi");
        System.out.println("   What can I do for you?");
        printLine();
    }

    /**
     * Prints the goodbye message before exit.
     */
    public void greetEnd() {
        printLine();
        System.out.println();
        System.out.println("   Bye. Hope to see you again soon!");
        printLine();
    }

    /**
     * Prints a horizontal separator line.
     */
    public void printLine() {
        System.out.println(LINE);
    }

    /**
     * Renders all tasks currently stored.
     */
    /**
     * Renders all tasks currently stored.
     *
     * @param tasks list of tasks to display
     */
    public void showTaskList(ArrayList<Task> tasks) {
        if (tasks.isEmpty()) {
            System.out.println("    You have no tasks in your list.");
            return;
        }
        System.out.println("    Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println("    " + (i + 1) + ". " + tasks.get(i).toString());
        }
    }

    /**
     * Shows confirmation that a task was added along with the new count.
     */
    /**
     * Shows confirmation that a task was added along with the new count.
     *
     * @param task  task that was added
     * @param total total number of tasks after addition
     */
    public void showTaskAdded(Task task, int total) {
        System.out.println("    Got it. I've added this task:");
        System.out.println("      " + task.toString());
        System.out.println("    Now you have " + total + " tasks in the list.");
    }

    /**
     * Displays tasks matching a search query.
     *
     * @param tasks matching tasks to render
     */
    public void showMatchingTasks(ArrayList<Task> tasks) {
        if (tasks.isEmpty()) {
            System.out.println("    No matching tasks found.");
            return;
        }
        System.out.println("    Here are the matching tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println("    " + (i + 1) + ". " + tasks.get(i).toString());
        }
    }

    public void showMarked(Task task) {
        System.out.println("    Nice! I've marked this task as done:");
        System.out.println("    " + task.toString());
    }

    /**
     * Shows confirmation for marking a task as not done.
     */
    /**
     * Shows confirmation for marking a task as not done.
     *
     * @param task task that was unmarked
     */
    public void showUnmarked(Task task) {
        System.out.println("    OK, I've marked this task as not done yet:");
        System.out.println("    " + task.toString());
    }

    /**
     * Prints an error message.
     */
    /**
     * Prints an error message.
     *
     * @param message text to display
     */
    public void showError(String message) {
        System.out.println("    " + message);
    }

    /**
     * Informs the user that the command was not recognised.
     */
    /**
     * Informs the user that the command was not recognised.
     *
     * @param command unknown command word
     */
    public void showUnknownCommand(String command) {
        System.out.println("    Unknown command: " + command);
        System.out.println("    Please try again, with \"todo\", \"deadline\", \"event\", \"mark\", \"unmark\", \"list\", \"find\", \"update\", or \"bye\".");
    }

    /**
     * Shows confirmation that a task was updated.
     *
     * @param task task that was updated
     */
    public void showTaskUpdated(Task task) {
        System.out.println("    Got it. I've updated this task:");
        System.out.println("      " + task.toString());
    }

    /**
     * Prints a generic message.
     *
     * @param message text to display
     */
    public void showMessage(String message) {
        System.out.println("    " + message);
    }
}
