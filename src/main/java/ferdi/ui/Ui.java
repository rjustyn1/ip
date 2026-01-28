package ferdi.ui;

import java.util.ArrayList;

import ferdi.task.Task;

public class Ui {
    private static final String LINE = "   ____________________________________________________________";
    private static final String LOGO = " ______           _ _\n" +
        "|  ____|         | (_)\n" +
        "| |__ ___ _ __ __| |_ \n" +
        "|  __/ _ \\ '__/ _` | |\n" +
        "| | |  __/ |   (_| | |\n" +
        "|_|  \\___|_|  \\__,_|_|\n";

    public void greetStart() {
        System.out.println("Hello from\n" + LOGO);
        printLine();
        System.out.println();
        System.out.println("   Hello! I think I my name is Ferdi");
        System.out.println("   What can I do for you?");
        printLine();
    }

    public void greetEnd() {
        printLine();
        System.out.println();
        System.out.println("   Bye. Hope to see you again soon!");
        printLine();
    }

    public void printLine() {
        System.out.println(LINE);
    }

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

    public void showTaskAdded(Task task, int total) {
        System.out.println("    Got it. I've added this task:");
        System.out.println("      " + task.toString());
        System.out.println("    Now you have " + total + " tasks in the list.");
    }

    public void showMarked(Task task) {
        System.out.println("    Nice! I've marked this task as done:");
        System.out.println("    " + task.toString());
    }

    public void showUnmarked(Task task) {
        System.out.println("    OK, I've marked this task as not done yet:");
        System.out.println("    " + task.toString());
    }

    public void showError(String message) {
        System.out.println("    " + message);
    }

    public void showUnknownCommand(String command) {
        System.out.println("    Unknown command: " + command);
        System.out.println("    Please try again, with \"todo\", \"deadline\", \"event\", \"mark\", \"unmark\", \"list\", or \"bye\".");
    }
}
