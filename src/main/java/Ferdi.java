import java.util.Scanner;

public class Ferdi {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Task[] taskList = new Task[100];
        int taskCount = 0;

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
                if (taskCount == 0) {
                    System.out.println("    You have no tasks in your list.");
                }
                else {
                    System.out.println("    Here are the tasks in your list:");
                }
                for (int i = 0; i < taskCount; i++) {
                    System.out.println("    " + (i + 1) + ". " + taskList[i].toString());
                }
            }
            else if (parsedCommand[0].equals("todo")){
                try {
                    ToDo newTask = ToDo.createFromCommand(commandArgs);
                    taskList[taskCount] = newTask;
                    taskCount++;
                    System.out.println("    Got it. I've added this task:");
                    System.out.println("      " + newTask.toString());
                    System.out.println("    Now you have " + taskCount + " tasks in the list.");
                } catch (IllegalArgumentException e) {
                    System.out.println("    " + e.getMessage());
                }
            }
            else if (parsedCommand[0].equals("deadline")){
                try {
                    Deadline newTask = Deadline.createFromCommand(commandArgs);
                    taskList[taskCount] = newTask;
                    taskCount++;
                    System.out.println("    Got it. I've added this task:");
                    System.out.println("      " + newTask.toString());
                    System.out.println("    Now you have " + taskCount + " tasks in the list.");
                } catch (IllegalArgumentException e) {
                    System.out.println("    " + e.getMessage());
                }
            }
            else if (parsedCommand[0].equals("event")){
                try {
                    Event newTask = Event.createFromCommand(commandArgs);
                    taskList[taskCount] = newTask;
                    taskCount++;
                    System.out.println("    Got it. I've added this task:");
                    System.out.println("      " + newTask.toString());
                    System.out.println("    Now you have " + taskCount + " tasks in the list.");
                } catch (IllegalArgumentException e) {
                    System.out.println("    " + e.getMessage());
                }
            }
            else if (parsedCommand[0].equals("mark")){
                try {
                    int taskNum = Integer.parseInt(parsedCommand[1]);
                    taskList[taskNum - 1].mark();
                    System.out.println("    Nice! I've marked this task as done:");
                    System.out.println("    " + taskList[taskNum - 1].toString());
                } catch (NumberFormatException e) {
                    System.out.println("    OOPS!!! Please provide a valid task number to mark.");
                } catch (Exception e) {
                    System.out.println("    There are only " + taskCount + " tasks in the list.");
                    System.out.println("    You cannot mark task number " + parsedCommand[1] + "." );
                }
            }
            else if (parsedCommand[0].equals("unmark")){
                try {
                    int taskNum = Integer.parseInt(parsedCommand[1]);
                    taskList[taskNum - 1].unmark();
                    System.out.println("    OK, I've marked this task as not done yet:");
                    System.out.println("    " + taskList[taskNum - 1].toString());
                } catch (NumberFormatException e) {
                    System.out.println("    OOPS!!! Please provide a valid task number to mark.");
                } catch (Exception e) {
                    System.out.println("    There are only " + taskCount + " tasks in the list.");
                    System.out.println("    You cannot mark task number " + parsedCommand[1] + "." );
                }
            }
            else {
                System.out.println("    " + "Unknown command: " + parsedCommand[0]);
                System.out.println("    " + "Please try again, with \"todo\", \"deadline\", \"event\", \"mark\", \"unmark\", \"list\", or \"bye\".");
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
