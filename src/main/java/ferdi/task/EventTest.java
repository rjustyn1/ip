package ferdi.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class EventTest {

    @Test
    void createFromCommand_valid_returnsEvent() {
        Event task = Event.createFromCommand("party /from Sat /to Sun");
        assertEquals("[E][ ] party (from: Sat, to: Sun)", task.toString());
        assertEquals("E | 0 | party | Sat | Sun", task.toFileFormat());
    }

    @Test
    void createFromCommand_missingTokens_throws() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> Event.createFromCommand("party /from Sat"));
        assertEquals("OOPS!!! Please specify event duration using both /from and /to.", ex.getMessage());
    }
}
