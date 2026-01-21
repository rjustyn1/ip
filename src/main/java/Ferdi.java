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
            System.out.println("   ____________________________________________________________\n");
            if (command.equals("list")) {
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
            else if (command.startsWith("mark")){
                int taskNum = Integer.parseInt(command.split(" ")[1]);
                taskList[taskNum - 1].mark();
                System.out.println("    Nice! I've marked this task as done:");
                System.out.println("    " + taskList[taskNum - 1].toString());
            }
            else if (command.startsWith("unmark")){
                int taskNum = Integer.parseInt(command.split(" ")[1]);
                taskList[taskNum - 1].unmark();
                System.out.println("    OK, I've marked this task as not done yet:");
                System.out.println("    " + taskList[taskNum - 1].toString());
            }
            else {
                System.out.println("    " + "added: " + command);
                taskCount++;
                taskList[taskCount - 1] = new Task(command);
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
