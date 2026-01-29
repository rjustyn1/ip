package ferdi.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class DeadlineTest {

    @Test
    void createFromCommand_valid_returnsDeadline() {
        Deadline task = Deadline.createFromCommand("project /by Sunday");
        assertEquals("[D][ ] project (by: Sunday)", task.toString());
        assertEquals("D | 0 | project | Sunday", task.toFileFormat());
    }

    @Test
    void createFromCommand_missingBy_throws() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> Deadline.createFromCommand("project"));
        assertEquals("OOPS!!! Please specify when the deadline is due using /by.", ex.getMessage());
    }
}
