import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Ferdi {
    private final Storage storage;
    private final TaskList tasks;
    private final Ui ui;

    public Ferdi(String filePath) {
        this.ui = new Ui();
        this.storage = new Storage(filePath);
        this.tasks = storage.load();
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        ui.greetStart();

        String command = scanner.nextLine();
        while (!command.equals("bye")) {
            String[] parsed = Parser.parse(command);
            String parsedCommand = parsed[0];
            String commandArgs = parsed[1];

            ui.printLine();

            if (parsedCommand.equals("list")) {
                ui.showTaskList(tasks.getTasks());
            } else if (parsedCommand.equals("todo")) {
                try {
                    ToDo newTask = ToDo.createFromCommand(commandArgs);
                    tasks.addTask(newTask);
                    storage.save(tasks);
                    ui.showTaskAdded(newTask, tasks.size());
                } catch (IllegalArgumentException e) {
                    ui.showError(e.getMessage());
                }
            } else if (parsedCommand.equals("deadline")) {
                try {
                    Deadline newTask = Deadline.createFromCommand(commandArgs);
                    tasks.addTask(newTask);
                    storage.save(tasks);
                    ui.showTaskAdded(newTask, tasks.size());
                } catch (IllegalArgumentException e) {
                    ui.showError(e.getMessage());
                }
            } else if (parsedCommand.equals("event")) {
                try {
                    Event newTask = Event.createFromCommand(commandArgs);
                    tasks.addTask(newTask);
                    storage.save(tasks);
                    ui.showTaskAdded(newTask, tasks.size());
                } catch (IllegalArgumentException e) {
                    ui.showError(e.getMessage());
                }
            } else if (parsedCommand.equals("mark")) {
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
            } else if (parsedCommand.equals("unmark")) {
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
            } else {
                ui.showUnknownCommand(parsedCommand);
            }

            ui.printLine();
            command = scanner.nextLine();
        }

        ui.greetEnd();
        scanner.close();
    }

    public static void main(String[] commandArgs) {
        new Ferdi("./src/main/java/data/ferdi.txt").run();
    }

    // Helper method to check if a task matches a given date
    private static boolean matchesDate(Task task, LocalDate date) {
        if (task instanceof Deadline) {
            Deadline d = (Deadline) task;
            return d.by.equals(date);
        } else if (task instanceof Event) {
            Event e = (Event) task;
            return e.from.equals(date) || e.to.equals(date);
        }
        return false;
    }
}
