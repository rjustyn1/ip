package ferdi.parser;

/**
 * Deals with parsing user input into command word and arguments.
 */
public class Parser {
    private static final String EMPTY_STRING = "";
    private static final int COMMAND_PARTS_LIMIT = 2;
    /**
     * Splits the raw user input into command word and command arguments.
     *
     * @param input full user input line
     * @return two-element array of command word and remaining arguments (may be empty)
     */
    public static String[] parse(String input) {
        if (input == null) {
            return new String[]{EMPTY_STRING, EMPTY_STRING};
        }

        String trimmedInput = input.trim();
        if (trimmedInput.isEmpty()) {
            return new String[]{EMPTY_STRING, EMPTY_STRING};
        }

        String[] parts = trimmedInput.split(" ", COMMAND_PARTS_LIMIT);
        String commandWord = parts[0];
        String commandArgs = (parts.length == COMMAND_PARTS_LIMIT) ? parts[1] : EMPTY_STRING;
        String[] result = new String[]{commandWord, commandArgs};
        assert result.length == 2 : "Parser should always return array of length 2";
        return result;
    }
}
