package ferdi;

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
        this.ui = new Ui();
        this.storage = new Storage(filePath);
        this.tasks = storage.load();
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
        return "Ferdi heard: " + input;
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
