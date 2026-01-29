package ferdi.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class ToDoTest {

    @Test
    void createFromCommand_valid_returnsToDo() {
        ToDo task = ToDo.createFromCommand("read book");
        assertEquals("[T][ ] read book", task.toString());
        assertEquals("T | 0 | read book", task.toFileFormat());
    }

    @Test
    void createFromCommand_empty_throws() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> ToDo.createFromCommand("   "));
        assertEquals("OOPS!!! The description of a todo cannot be empty.", ex.getMessage());
    }
}
