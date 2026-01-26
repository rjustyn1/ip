import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Ferdi {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Storage storage = new Storage("./src/main/java/data/ferdi.txt");

        String logo = " ______           _ _\n" +
            "|  ____|         | (_)\n" +
            "| |__ ___ _ __ __| |_ \n" +
            "|  __/ _ \\ '__/ _` | |\n" +
            "| | |  __/ |   (_| | |\n" +
            "|_|  \\___|_|  \\__,_|_|\n";

        System.out.println("Hello from\n" + logo);

        String greetStart = "   ____________________________________________________________\n" +
            "   Hello! I think I my name is Ferdi\n" +
            "   What can I do for you?\n" +
            "   ____________________________________________________________\n";

        System.out.println(greetStart);

        String command = scanner.nextLine();
        while (!command.equals("bye")) {
            String[] parsedCommand = command.split(" ", 2);
            String commandArgs = parsedCommand.length > 1 ? parsedCommand[1] : "";

            System.out.println("   ____________________________________________________________\n");

            if (parsedCommand[0].equals("list")) {
                if (storage.size() == 0) {
                    System.out.println("    You have no tasks in your list.");
                }
                else {
                    System.out.println("    Here are the tasks in your list:");
                }
                for (int i = 0; i < storage.size(); i++) {
                    System.out.println("    " + (i + 1) + ". " + storage.getTask(i).toString());
                }
            }
            else if (parsedCommand[0].equals("todo")){
                try {
                    ToDo newTask = ToDo.createFromCommand(commandArgs);
                    storage.addTask(newTask);
                    System.out.println("    Got it. I've added this task:");
                    System.out.println("      " + newTask.toString());
                    System.out.println("    Now you have " + storage.size() + " tasks in the list.");
                } catch (IllegalArgumentException e) {
                    System.out.println("    " + e.getMessage());
                }
            }
            else if (parsedCommand[0].equals("deadline")){
                try {
                    Deadline newTask = Deadline.createFromCommand(commandArgs);
                    storage.addTask(newTask);
                    System.out.println("    Got it. I've added this task:");
                    System.out.println("      " + newTask.toString());
                    System.out.println("    Now you have " + storage.size() + " tasks in the list.");
                } catch (IllegalArgumentException e) {
                    System.out.println("    " + e.getMessage());
                }
            }
            else if (parsedCommand[0].equals("event")){
                try {
                    Event newTask = Event.createFromCommand(commandArgs);
                    storage.addTask(newTask);
                    System.out.println("    Got it. I've added this task:");
                    System.out.println("      " + newTask.toString());
                    System.out.println("    Now you have " + storage.size() + " tasks in the list.");
                } catch (IllegalArgumentException e) {
                    System.out.println("    " + e.getMessage());
                }
            }
            else if (parsedCommand[0].equals("mark")){
                try {
                    int taskNum = Integer.parseInt(parsedCommand[1]);
                    storage.markTask(taskNum - 1);
                    System.out.println("    Nice! I've marked this task as done:");
                    System.out.println("    " + storage.getTask(taskNum - 1).toString());
                } catch (NumberFormatException e) {
                    System.out.println("    OOPS!!! Please provide a valid task number to mark.");
                } catch (Exception e) {
                    System.out.println("    There are only " + storage.size() + " tasks in the list.");
                    System.out.println("    You cannot mark task number " + parsedCommand[1] + "." );
                }
            }
            else if (parsedCommand[0].equals("unmark")){
                try {
                    int taskNum = Integer.parseInt(parsedCommand[1]);
                    storage.unmarkTask(taskNum - 1);
                    System.out.println("    OK, I've marked this task as not done yet:");
                    System.out.println("    " + storage.getTask(taskNum - 1).toString());
                } catch (NumberFormatException e) {
                    System.out.println("    OOPS!!! Please provide a valid task number to mark.");
                } catch (Exception e) {
                    System.out.println("    There are only " + storage.size() + " tasks in the list.");
                    System.out.println("    You cannot mark task number " + parsedCommand[1] + "." );
                }
            }
            else if (parsedCommand[0].equals("today")){
                LocalDate today = LocalDate.now();
                boolean found = false;
                System.out.println("    Tasks due or happening today (" + today.format(DateTimeFormatter.ofPattern("MMM dd yyyy")) + "):");
                for (int i = 0; i < storage.size(); i++) {
                    Task t = storage.getTask(i);
                    if (matchesDate(t, today)) {
                        System.out.println("    " + (i + 1) + ". " + t.toString());
                        found = true;
                    }
                }
                if (!found) {
                    System.out.println("    No tasks for today.");
                }
            }
            else if (parsedCommand[0].equals("on")){
                if (commandArgs.isEmpty()) {
                    System.out.println("    OOPS!!! Please provide a date in yyyy-MM-dd format (e.g., 2019-12-02).");
                } else {
                    try {
                        LocalDate searchDate = LocalDate.parse(commandArgs.trim(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                        boolean found = false;
                        System.out.println("    Tasks on " + searchDate.format(DateTimeFormatter.ofPattern("MMM dd yyyy")) + ":");
                        for (int i = 0; i < storage.size(); i++) {
                            Task t = storage.getTask(i);
                            if (matchesDate(t, searchDate)) {
                                System.out.println("    " + (i + 1) + ". " + t.toString());
                                found = true;
                            }
                        }
                        if (!found) {
                            System.out.println("    No tasks found for that date.");
                        }
                    } catch (DateTimeParseException e) {
                        System.out.println("    OOPS!!! Please provide date in yyyy-MM-dd format (e.g., 2019-12-02).");
                    }
                }
            }
            else {
                System.out.println("    " + "Unknown command: " + parsedCommand[0]);
                System.out.println("    " + "Please try again, with \"todo\", \"deadline\", \"event\", \"mark\", \"unmark\", \"list\", \"today\", \"on\", or \"bye\".");
            }

            System.out.println("   ____________________________________________________________\n");

            command = scanner.nextLine();
        }

        String greetEnd = "    ____________________________________________________________\n" +
            "   Bye. Hope to see you again soon!\n" +
            "   ____________________________________________________________\n";
        
        System.out.println(greetEnd);
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
