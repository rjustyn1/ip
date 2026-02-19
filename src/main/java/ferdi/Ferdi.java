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
            ui.printLine();
            handleCommand(command);
            ui.printLine();
            command = scanner.nextLine();
        }

        ui.greetEnd();
        scanner.close();
    }

    /**
     * Handles a single command in the CLI mode.
     *
     * @param command the command string from user input
     */
    private void handleCommand(String command) {
        String[] parsed = Parser.parse(command);
        String parsedCommand = parsed[0];
        String commandArgs = parsed[1];

        if (parsedCommand.equals(CMD_LIST)) {
            handleListCommand();
        } else if (parsedCommand.equals(CMD_TODO)) {
            handleAddTaskCommand(commandArgs, "todo");
        } else if (parsedCommand.equals(CMD_DEADLINE)) {
            handleAddTaskCommand(commandArgs, "deadline");
        } else if (parsedCommand.equals(CMD_EVENT)) {
            handleAddTaskCommand(commandArgs, "event");
        } else if (parsedCommand.equals(CMD_MARK)) {
            handleMarkCommand(commandArgs, true);
        } else if (parsedCommand.equals(CMD_UNMARK)) {
            handleMarkCommand(commandArgs, false);
        } else if (parsedCommand.equals(CMD_FIND)) {
            handleFindCommand(commandArgs);
        } else if (parsedCommand.equals(CMD_UPDATE)) {
            handleUpdateCommand(commandArgs);
        } else {
            ui.showUnknownCommand(parsedCommand);
        }
    }

    /**
     * Handles the list command.
     */
    private void handleListCommand() {
        ui.showTaskList(tasks.getTasks());
    }

    /**
     * Handles add task commands (todo, deadline, event).
     *
     * @param commandArgs the task description and details
     * @param taskType the type of task ("todo", "deadline", or "event")
     */
    private void handleAddTaskCommand(String commandArgs, String taskType) {
        try {
            ferdi.task.Task newTask = switch (taskType) {
                case "todo" -> ToDo.createFromCommand(commandArgs);
                case "deadline" -> Deadline.createFromCommand(commandArgs);
                case "event" -> Event.createFromCommand(commandArgs);
                default -> throw new IllegalArgumentException("Unknown task type");
            };
            tasks.addTask(newTask);
            storage.save(tasks);
            ui.showTaskAdded(newTask, tasks.size());
        } catch (IllegalArgumentException e) {
            ui.showError(e.getMessage());
        }
    }

    /**
     * Handles mark and unmark commands.
     *
     * @param commandArgs the task number to mark/unmark
     * @param isMark true for mark, false for unmark
     */
    private void handleMarkCommand(String commandArgs, boolean isMark) {
        try {
            int taskNum = Integer.parseInt(commandArgs);
            if (isMark) {
                tasks.markTask(taskNum - 1);
                ui.showMarked(tasks.getTask(taskNum - 1));
            } else {
                tasks.unmarkTask(taskNum - 1);
                ui.showUnmarked(tasks.getTask(taskNum - 1));
            }
            storage.save(tasks);
        } catch (NumberFormatException e) {
            String action = isMark ? "mark" : "unmark";
            ui.showError("OOPS!!! Please provide a valid task number to " + action + ".");
        } catch (Exception e) {
            ui.showError("There are only " + tasks.size() + " tasks in the list.");
            ui.showError("You cannot " + (isMark ? "mark" : "unmark") + " task number " + commandArgs + ".");
        }
    }

    /**
     * Handles the find command.
     *
     * @param commandArgs the keyword to search for
     */
    private void handleFindCommand(String commandArgs) {
        if (commandArgs.trim().isEmpty()) {
            ui.showError("OOPS!!! Please provide a keyword to find.");
        } else {
            ui.showMatchingTasks(tasks.findTasks(commandArgs.trim()));
        }
    }

    /**
     * Handles the update command in CLI mode.
     *
     * @param commandArgs the task number and fields to update
     */
    private void handleUpdateCommand(String commandArgs) {
        try {
            updateTask(commandArgs);
        } catch (IllegalArgumentException e) {
            ui.showError(e.getMessage());
        } catch (Exception e) {
            ui.showError("There are only " + tasks.size() + " tasks in the list.");
            ui.showError("You cannot update task number " + commandArgs.split(" ")[0] + ".");
        }
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

        return switch (parsedCommand) {
            case CMD_LIST -> getListResponse();
            case CMD_TODO -> getAddTaskResponse(commandArgs, "todo");
            case CMD_DEADLINE -> getAddTaskResponse(commandArgs, "deadline");
            case CMD_EVENT -> getAddTaskResponse(commandArgs, "event");
            case CMD_MARK -> getMarkResponse(commandArgs, true);
            case CMD_UNMARK -> getMarkResponse(commandArgs, false);
            case CMD_FIND -> getFindResponse(commandArgs);
            case CMD_UPDATE -> getUpdateResponse(commandArgs);
            default -> getUnknownCommandResponse(parsedCommand);
        };
    }

    /**
     * Generates the response for the list command.
     *
     * @return the response string
     */
    private String getListResponse() {
        if (tasks.getTasks().isEmpty()) {
            return "You have no tasks in your list.";
        }
        StringBuilder response = new StringBuilder("Here are the tasks in your list:\n");
        for (int i = 0; i < tasks.size(); i++) {
            response.append((i + 1)).append(". ").append(tasks.getTask(i).toString()).append("\n");
        }
        return response.toString();
    }

    /**
     * Generates the response for add task commands (todo, deadline, event).
     *
     * @param commandArgs the task description and details
     * @param taskType the type of task ("todo", "deadline", or "event")
     * @return the response string
     */
    private String getAddTaskResponse(String commandArgs, String taskType) {
        try {
            ferdi.task.Task newTask = switch (taskType) {
                case "todo" -> ToDo.createFromCommand(commandArgs);
                case "deadline" -> Deadline.createFromCommand(commandArgs);
                case "event" -> Event.createFromCommand(commandArgs);
                default -> throw new IllegalArgumentException("Unknown task type");
            };
            tasks.addTask(newTask);
            storage.save(tasks);
            return "Got it. I've added this task:\n  "
                    + newTask.toString()
                    + "\nNow you have " + tasks.size() + " tasks in the list.";
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
    }

    /**
     * Generates the response for mark and unmark commands.
     *
     * @param commandArgs the task number to mark/unmark
     * @param isMark true for mark, false for unmark
     * @return the response string
     */
    private String getMarkResponse(String commandArgs, boolean isMark) {
        try {
            int taskNum = Integer.parseInt(commandArgs);
            if (isMark) {
                tasks.markTask(taskNum - 1);
                String response = "Nice! I've marked this task as done:\n"
                        + tasks.getTask(taskNum - 1).toString();
                storage.save(tasks);
                return response;
            } else {
                tasks.unmarkTask(taskNum - 1);
                String response = "OK, I've marked this task as not done yet:\n"
                        + tasks.getTask(taskNum - 1).toString();
                storage.save(tasks);
                return response;
            }
        } catch (NumberFormatException e) {
            String action = isMark ? "mark" : "unmark";
            return "OOPS!!! Please provide a valid task number to " + action + ".";
        } catch (Exception e) {
            return "There are only " + tasks.size() + " tasks in the list.\n"
                    + "You cannot " + (isMark ? "mark" : "unmark") + " task number " + commandArgs + ".";
        }
    }

    /**
     * Generates the response for the find command.
     *
     * @param commandArgs the keyword to search for
     * @return the response string
     */
    private String getFindResponse(String commandArgs) {
        if (commandArgs.trim().isEmpty()) {
            return "OOPS!!! Please provide a keyword to find.";
        }
        ArrayList<ferdi.task.Task> matchingTasks = tasks.findTasks(commandArgs.trim());
        if (matchingTasks.isEmpty()) {
            return "No matching tasks found.";
        }
        StringBuilder response = new StringBuilder("Here are the matching tasks in your list:\n");
        for (int i = 0; i < matchingTasks.size(); i++) {
            response.append((i + 1)).append(". ").append(matchingTasks.get(i).toString()).append("\n");
        }
        return response.toString();
    }

    /**
     * Generates the response for the update command in chat mode.
     *
     * @param commandArgs the task number and fields to update
     * @return the response string
     */
    private String getUpdateResponse(String commandArgs) {
        try {
            ferdi.task.Task updatedTask = updateTask(commandArgs);
            return "Got it. I've updated this task:\n  " + updatedTask.toString();
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        } catch (Exception e) {
            return "There are only " + tasks.size() + " tasks in the list.\n"
                    + "You cannot update task number " + commandArgs.split(" ")[0] + ".";
        }
    }

    /**
     * Generates the response for unknown commands.
     *
     * @param command the unknown command
     * @return the response string
     */
    private String getUnknownCommandResponse(String command) {
        return "Unknown command: " + command
                + "\nPlease try again, with \"todo\", \"deadline\", \"event\", \"mark\", \"unmark\", \"list\", \"find\", \"update\", or \"bye\".";
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
