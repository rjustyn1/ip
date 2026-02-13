package ferdi;

import java.util.ArrayList;
import java.util.Scanner;

import ferdi.parser.Parser;
import ferdi.storage.Storage;
import ferdi.task.Deadline;
import ferdi.task.Event;
import ferdi.task.TaskList;
import ferdi.task.ToDo;
import ferdi.ui.Ui;

/**
 * Entry point for the Ferdi task manager CLI application.
 */
public class Ferdi {
    private static final String CMD_BYE = "bye";
    private static final String CMD_LIST = "list";
    private static final String CMD_TODO = "todo";
    private static final String CMD_DEADLINE = "deadline";
    private static final String CMD_EVENT = "event";
    private static final String CMD_MARK = "mark";
    private static final String CMD_UNMARK = "unmark";
    private static final String CMD_FIND = "find";
    private static final String CMD_UPDATE = "update";
    private static final String DEFAULT_DATA_PATH = "./src/main/java/data/ferdi.txt";

    private final Storage storage;
    private final TaskList tasks;
    private final Ui ui;

    /**
     * Constructs the app with storage bound to the provided file path.
     * Loads any existing tasks into memory before running.
     *
     * @param filePath path to the data file
     */
    public Ferdi(String filePath) {
        assert filePath != null : "File path cannot be null";
        this.ui = new Ui();
        this.storage = new Storage(filePath);
        this.tasks = storage.load();
        assert this.tasks != null : "TaskList should never be null after loading";
    }

    /**
     * Starts the interactive loop until the user types "bye".
     */
    public void run() {
        Scanner scanner = new Scanner(System.in);
        ui.greetStart();

        String command = scanner.nextLine();
        while (!command.equals(CMD_BYE)) {
            String[] parsed = Parser.parse(command);
            String parsedCommand = parsed[0];
            String commandArgs = parsed[1];

            ui.printLine();

            if (parsedCommand.equals(CMD_LIST)) {
                ui.showTaskList(tasks.getTasks());
            } else if (parsedCommand.equals(CMD_TODO)) {
                try {
                    ToDo newTask = ToDo.createFromCommand(commandArgs);
                    tasks.addTask(newTask);
                    storage.save(tasks);
                    ui.showTaskAdded(newTask, tasks.size());
                } catch (IllegalArgumentException e) {
                    ui.showError(e.getMessage());
                }
            } else if (parsedCommand.equals(CMD_DEADLINE)) {
                try {
                    Deadline newTask = Deadline.createFromCommand(commandArgs);
                    tasks.addTask(newTask);
                    storage.save(tasks);
                    ui.showTaskAdded(newTask, tasks.size());
                } catch (IllegalArgumentException e) {
                    ui.showError(e.getMessage());
                }
            } else if (parsedCommand.equals(CMD_EVENT)) {
                try {
                    Event newTask = Event.createFromCommand(commandArgs);
                    tasks.addTask(newTask);
                    storage.save(tasks);
                    ui.showTaskAdded(newTask, tasks.size());
                } catch (IllegalArgumentException e) {
                    ui.showError(e.getMessage());
                }
            } else if (parsedCommand.equals(CMD_MARK)) {
                try {
                    int taskNum = Integer.parseInt(commandArgs);
                    tasks.markTask(taskNum - 1);
                    storage.save(tasks);
                    ui.showMarked(tasks.getTask(taskNum - 1));
                } catch (NumberFormatException e) {
                    ui.showError("OOPS!!! Please provide a valid task number to mark.");
                } catch (Exception e) {
                    ui.showError("There are only " + tasks.size() + " tasks in the list.");
                    ui.showError("You cannot mark task number " + commandArgs + ".");
                }
            } else if (parsedCommand.equals(CMD_UNMARK)) {
                try {
                    int taskNum = Integer.parseInt(commandArgs);
                    tasks.unmarkTask(taskNum - 1);
                    storage.save(tasks);
                    ui.showUnmarked(tasks.getTask(taskNum - 1));
                } catch (NumberFormatException e) {
                    ui.showError("OOPS!!! Please provide a valid task number to unmark.");
                } catch (Exception e) {
                    ui.showError("There are only " + tasks.size() + " tasks in the list.");
                    ui.showError("You cannot unmark task number " + commandArgs + ".");
                }
            } else if (parsedCommand.equals(CMD_FIND)) {
                if (commandArgs.trim().isEmpty()) {
                    ui.showError("OOPS!!! Please provide a keyword to find.");
                } else {
                    ui.showMatchingTasks(tasks.findTasks(commandArgs.trim()));
                }
            } else if (parsedCommand.equals(CMD_UPDATE)) {
                try {
                    updateTask(commandArgs);
                } catch (IllegalArgumentException e) {
                    ui.showError(e.getMessage());
                } catch (Exception e) {
                    ui.showError("There are only " + tasks.size() + " tasks in the list.");
                    ui.showError("You cannot update task number " + commandArgs.split(" ")[0] + ".");
                }
            } else {
                ui.showUnknownCommand(parsedCommand);
            }

            ui.printLine();
            command = scanner.nextLine();
        }

        ui.greetEnd();
        scanner.close();
    }

    /**
     * Generates a response for the user's chat message.
     */
    public String getResponse(String input) {
        if (input.equals(CMD_BYE)) {
            return "Bye. Hope to see you again soon!";
        }

        String[] parsed = Parser.parse(input);
        String parsedCommand = parsed[0];
        String commandArgs = parsed[1];

        StringBuilder response = new StringBuilder();

        if (parsedCommand.equals(CMD_LIST)) {
            if (tasks.getTasks().isEmpty()) {
                response.append("You have no tasks in your list.");
            } else {
                response.append("Here are the tasks in your list:\n");
                for (int i = 0; i < tasks.size(); i++) {
                    response.append((i + 1)).append(". ").append(tasks.getTask(i).toString()).append("\n");
                }
            }
        } else if (parsedCommand.equals(CMD_TODO)) {
            try {
                ToDo newTask = ToDo.createFromCommand(commandArgs);
                tasks.addTask(newTask);
                storage.save(tasks);
                response.append("Got it. I've added this task:\n  ")
                        .append(newTask.toString())
                        .append("\nNow you have ").append(tasks.size()).append(" tasks in the list.");
            } catch (IllegalArgumentException e) {
                response.append(e.getMessage());
            }
        } else if (parsedCommand.equals(CMD_DEADLINE)) {
            try {
                Deadline newTask = Deadline.createFromCommand(commandArgs);
                tasks.addTask(newTask);
                storage.save(tasks);
                response.append("Got it. I've added this task:\n  ")
                        .append(newTask.toString())
                        .append("\nNow you have ").append(tasks.size()).append(" tasks in the list.");
            } catch (IllegalArgumentException e) {
                response.append(e.getMessage());
            }
        } else if (parsedCommand.equals(CMD_EVENT)) {
            try {
                Event newTask = Event.createFromCommand(commandArgs);
                tasks.addTask(newTask);
                storage.save(tasks);
                response.append("Got it. I've added this task:\n  ")
                        .append(newTask.toString())
                        .append("\nNow you have ").append(tasks.size()).append(" tasks in the list.");
            } catch (IllegalArgumentException e) {
                response.append(e.getMessage());
            }
        } else if (parsedCommand.equals(CMD_MARK)) {
            try {
                int taskNum = Integer.parseInt(commandArgs);
                tasks.markTask(taskNum - 1);
                storage.save(tasks);
                response.append("Nice! I've marked this task as done:\n")
                        .append(tasks.getTask(taskNum - 1).toString());
            } catch (NumberFormatException e) {
                response.append("OOPS!!! Please provide a valid task number to mark.");
            } catch (Exception e) {
                response.append("There are only ").append(tasks.size()).append(" tasks in the list.\n")
                        .append("You cannot mark task number ").append(commandArgs).append(".");
            }
        } else if (parsedCommand.equals(CMD_UNMARK)) {
            try {
                int taskNum = Integer.parseInt(commandArgs);
                tasks.unmarkTask(taskNum - 1);
                storage.save(tasks);
                response.append("OK, I've marked this task as not done yet:\n")
                        .append(tasks.getTask(taskNum - 1).toString());
            } catch (NumberFormatException e) {
                response.append("OOPS!!! Please provide a valid task number to unmark.");
            } catch (Exception e) {
                response.append("There are only ").append(tasks.size()).append(" tasks in the list.\n")
                        .append("You cannot unmark task number ").append(commandArgs).append(".");
            }
        } else if (parsedCommand.equals(CMD_FIND)) {
            if (commandArgs.trim().isEmpty()) {
                response.append("OOPS!!! Please provide a keyword to find.");
            } else {
                ArrayList<ferdi.task.Task> matchingTasks = tasks.findTasks(commandArgs.trim());
                if (matchingTasks.isEmpty()) {
                    response.append("No matching tasks found.");
                } else {
                    response.append("Here are the matching tasks in your list:\n");
                    for (int i = 0; i < matchingTasks.size(); i++) {
                        response.append((i + 1)).append(". ").append(matchingTasks.get(i).toString()).append("\n");
                    }
                }
            }
        } else if (parsedCommand.equals(CMD_UPDATE)) {
            try {
                ferdi.task.Task updatedTask = updateTask(commandArgs);
                response.append("Got it. I've updated this task:\n  ")
                        .append(updatedTask.toString());
            } catch (IllegalArgumentException e) {
                response.append(e.getMessage());
            } catch (Exception e) {
                response.append("There are only ").append(tasks.size()).append(" tasks in the list.\n")
                        .append("You cannot update task number ").append(commandArgs.split(" ")[0]).append(".");
            }
        } else {
            response.append("Unknown command: ").append(parsedCommand)
                    .append("\nPlease try again, with \"todo\", \"deadline\", \"event\", \"mark\", \"unmark\", \"list\", \"find\", \"update\", or \"bye\".");
        }

        return response.toString();
    }

    /**
     * Updates a task based on the command arguments.
     * Format: update TASK_NUM /field NEW_VALUE
     * Supported fields: /by (for deadline), /from (for event), /to (for event)
     *
     * @param commandArgs command arguments
     * @return the updated task
     * @throws IllegalArgumentException if command format is invalid
     * @throws IndexOutOfBoundsException if task number is out of range
     */
    private ferdi.task.Task updateTask(String commandArgs) throws IllegalArgumentException {
        if (commandArgs == null || commandArgs.trim().isEmpty()) {
            throw new IllegalArgumentException("OOPS!!! Please provide the task number and field to update.");
        }

        String[] parts = commandArgs.trim().split(" ", 2);
        if (parts.length < 2) {
            throw new IllegalArgumentException("OOPS!!! Please specify what to update (e.g., update 1 /by 2024-12-31).");
        }

        int taskNum = Integer.parseInt(parts[0]);
        ferdi.task.Task task = tasks.getTask(taskNum - 1);
        String updateArgs = parts[1];

        if (task instanceof Deadline) {
            ((Deadline) task).updateFromCommand(updateArgs);
        } else if (task instanceof Event) {
            ((Event) task).updateFromCommand(updateArgs);
        } else {
            throw new IllegalArgumentException("OOPS!!! ToDo tasks cannot be updated. Only Deadline and Event tasks can be modified.");
        }

        storage.save(tasks);
        ui.showTaskUpdated(task);
        return task;
    }

    /**
     * Bootstraps the application.
     *
     * @param commandArgs command line arguments (unused)
     */
    public static void main(String[] commandArgs) {
        new Ferdi(DEFAULT_DATA_PATH).run();
    }
}
