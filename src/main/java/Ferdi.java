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
            String[] parsedCommand = command.split(" ");

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
                String description = command.substring(5);
                ToDo newTask = new ToDo(description);
                taskList[taskCount] = newTask;
                taskCount++;
                System.out.println("    Got it. I've added this task:");
                System.out.println("      " + newTask.toString());
                System.out.println("    Now you have " + taskCount + " tasks in the list.");
            }
            else if (parsedCommand[0].equals("deadline")){
                String[] parts = command.substring(9).split(" /by ");
                String description = parts[0];
                String by = parts[1];
                Deadline newTask = new Deadline(description, by);
                taskList[taskCount] = newTask;
                taskCount++;
                System.out.println("    Got it. I've added this task:");
                System.out.println("      " + newTask.toString());
                System.out.println("    Now you have " + taskCount + " tasks in the list.");
            }
            else if (parsedCommand[0].equals("event")){
                String[] parts = command.substring(6).split(" /from | /to ");
                String description = parts[0];
                String from = parts[1];
                String to = parts[2];
                Event newTask = new Event(description, from, to);
                taskList[taskCount] = newTask;
                taskCount++;
                System.out.println("    Got it. I've added this task:");
                System.out.println("      " + newTask.toString());
                System.out.println("    Now you have " + taskCount + " tasks in the list.");

            }
            else if (parsedCommand[0].equals("mark")){
                int taskNum = Integer.parseInt(parsedCommand[1]);
                taskList[taskNum - 1].mark();
                System.out.println("    Nice! I've marked this task as done:");
                System.out.println("    " + taskList[taskNum - 1].toString());
            }
            else if (parsedCommand[0].equals("unmark")){
                int taskNum = Integer.parseInt(parsedCommand[1]);
                taskList[taskNum - 1].unmark();
                System.out.println("    OK, I've marked this task as not done yet:");
                System.out.println("    " + taskList[taskNum - 1].toString());
            }
            else {
                System.out.println("    " + "Unknown command: " + parsedCommand[0]);
                System.out.println("    " + "Please try again.");
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
