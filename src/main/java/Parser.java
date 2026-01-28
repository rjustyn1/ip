/**
 * Deals with parsing user input into command word and arguments.
 */
public class Parser {
    /**
     * Splits the raw user input into command word and command arguments.
     */
    public static String[] parse(String input) {
        if (input == null) {
            return new String[]{"", ""};
        }
        String[] parts = input.split(" ", 2);
        String commandWord = parts.length > 0 ? parts[0] : "";
        String commandArgs = parts.length > 1 ? parts[1] : "";
        return new String[]{commandWord, commandArgs};
    }
}