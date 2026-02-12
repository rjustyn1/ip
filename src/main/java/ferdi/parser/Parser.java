package ferdi.parser;

/**
 * Deals with parsing user input into command word and arguments.
 */
public class Parser {
    /**
     * Splits the raw user input into command word and command arguments.
     *
     * @param input full user input line
     * @return two-element array of command word and remaining arguments (may be empty)
     */
    public static String[] parse(String input) {
        if (input == null) {
            return new String[]{"", ""};
        }
        String[] parts = input.split(" ", 2);
        String commandWord = parts.length > 0 ? parts[0] : "";
        String commandArgs = parts.length > 1 ? parts[1] : "";
        String[] result = new String[]{commandWord, commandArgs};
        assert result.length == 2 : "Parser should always return array of length 2";
        return result;
    }
}
