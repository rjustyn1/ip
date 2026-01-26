<<<<<<< HEAD
import java.util.ArrayList;
=======
>>>>>>> branch-Level-7
import java.util.Scanner;

public class Ferdi {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
<<<<<<< HEAD
        ArrayList<Task> taskList = new ArrayList<>();
=======
        Storage storage = new Storage("./src/main/java/data/ferdi.txt");
>>>>>>> branch-Level-7

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
<<<<<<< HEAD
                if (taskList.isEmpty()) {
=======
                if (storage.size() == 0) {
>>>>>>> branch-Level-7
                    System.out.println("    You have no tasks in your list.");
                }
                else {
                    System.out.println("    Here are the tasks in your list:");
                }
<<<<<<< HEAD
                for (int i = 0; i < taskList.size(); i++) {
                    System.out.println("    " + (i + 1) + ". " + taskList.get(i).toString());
=======
                for (int i = 0; i < storage.size(); i++) {
                    System.out.println("    " + (i + 1) + ". " + storage.getTask(i).toString());
>>>>>>> branch-Level-7
                }
            }
            else if (parsedCommand[0].equals("todo")){
                try {
                    ToDo newTask = ToDo.createFromCommand(commandArgs);
<<<<<<< HEAD
                    taskList.add(newTask);
                    System.out.println("    Got it. I've added this task:");
                    System.out.println("      " + newTask.toString());
                    System.out.println("    Now you have " + taskList.size() + " tasks in the list.");
=======
                    storage.addTask(newTask);
                    System.out.println("    Got it. I've added this task:");
                    System.out.println("      " + newTask.toString());
                    System.out.println("    Now you have " + storage.size() + " tasks in the list.");
>>>>>>> branch-Level-7
                } catch (IllegalArgumentException e) {
                    System.out.println("    " + e.getMessage());
                }
            }
            else if (parsedCommand[0].equals("deadline")){
                try {
                    Deadline newTask = Deadline.createFromCommand(commandArgs);
<<<<<<< HEAD
                    taskList.add(newTask);
                    System.out.println("    Got it. I've added this task:");
                    System.out.println("      " + newTask.toString());
                    System.out.println("    Now you have " + taskList.size() + " tasks in the list.");
=======
                    storage.addTask(newTask);
                    System.out.println("    Got it. I've added this task:");
                    System.out.println("      " + newTask.toString());
                    System.out.println("    Now you have " + storage.size() + " tasks in the list.");
>>>>>>> branch-Level-7
                } catch (IllegalArgumentException e) {
                    System.out.println("    " + e.getMessage());
                }
            }
            else if (parsedCommand[0].equals("event")){
                try {
                    Event newTask = Event.createFromCommand(commandArgs);
<<<<<<< HEAD
                    taskList.add(newTask);
                    System.out.println("    Got it. I've added this task:");
                    System.out.println("      " + newTask.toString());
                    System.out.println("    Now you have " + taskList.size() + " tasks in the list.");
=======
                    storage.addTask(newTask);
                    System.out.println("    Got it. I've added this task:");
                    System.out.println("      " + newTask.toString());
                    System.out.println("    Now you have " + storage.size() + " tasks in the list.");
>>>>>>> branch-Level-7
                } catch (IllegalArgumentException e) {
                    System.out.println("    " + e.getMessage());
                }
            }
            else if (parsedCommand[0].equals("mark")){
                try {
                    int taskNum = Integer.parseInt(parsedCommand[1]);
<<<<<<< HEAD
                    taskList.get(taskNum - 1).mark();
                    System.out.println("    Nice! I've marked this task as done:");
                    System.out.println("    " + taskList.get(taskNum - 1).toString());
                } catch (NumberFormatException e) {
                    System.out.println("    OOPS!!! Please provide a valid task number to mark.");
                } catch (Exception e) {
                    System.out.println("    There are only " + taskList.size() + " tasks in the list.");
=======
                    storage.markTask(taskNum - 1);
                    System.out.println("    Nice! I've marked this task as done:");
                    System.out.println("    " + storage.getTask(taskNum - 1).toString());
                } catch (NumberFormatException e) {
                    System.out.println("    OOPS!!! Please provide a valid task number to mark.");
                } catch (Exception e) {
                    System.out.println("    There are only " + storage.size() + " tasks in the list.");
>>>>>>> branch-Level-7
                    System.out.println("    You cannot mark task number " + parsedCommand[1] + "." );
                }
            }
            else if (parsedCommand[0].equals("unmark")){
                try {
                    int taskNum = Integer.parseInt(parsedCommand[1]);
<<<<<<< HEAD
                    taskList.get(taskNum - 1).unmark();
                    System.out.println("    OK, I've marked this task as not done yet:");
                    System.out.println("    " + taskList.get(taskNum - 1).toString());
                } catch (NumberFormatException e) {
                    System.out.println("    OOPS!!! Please provide a valid task number to mark.");
                } catch (Exception e) {
                    System.out.println("    There are only " + taskList.size() + " tasks in the list.");
                    System.out.println("    You cannot mark task number " + parsedCommand[1] + "." );
                }
            }
            else if (parsedCommand[0].equals("delete")){
                try {
                    if (parsedCommand.length < 2) {
                        throw new IllegalArgumentException("OOPS!!! Please specify which task number to delete.");
                    }
                    int taskNum = Integer.parseInt(parsedCommand[1]);
                    Task removedTask = taskList.remove(taskNum - 1);
                    System.out.println("    Noted. I've removed this task:");
                    System.out.println("      " + removedTask.toString());
                    System.out.println("    Now you have " + taskList.size() + " tasks in the list.");
                } catch (NumberFormatException e) {
                    System.out.println("    OOPS!!! Please provide a valid task number to delete.");
                } catch (Exception e) {
                    System.out.println("    There are only " + taskList.size() + " tasks in the list.");
                    System.out.println("    You cannot delete task number " + parsedCommand[1] + "." );
                }
            }
            else {
                System.out.println("    " + "Unknown command: " + parsedCommand[0]);
                System.out.println("    " + "Please try again, with \"todo\", \"deadline\", \"event\", \"mark\", \"unmark\", \"delete\", \"list\", or \"bye\".");
=======
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
            else {
                System.out.println("    " + "Unknown command: " + parsedCommand[0]);
                System.out.println("    " + "Please try again, with \"todo\", \"deadline\", \"event\", \"mark\", \"unmark\", \"list\", or \"bye\".");
>>>>>>> branch-Level-7
            }

            System.out.println("   ____________________________________________________________\n");

            command = scanner.nextLine();
        }

        String greetEnd = "    ____________________________________________________________\n" +
            "   Bye. Hope to see you again soon!\n" +
            "   ____________________________________________________________\n";
        
        System.out.println(greetEnd);
    }
}
