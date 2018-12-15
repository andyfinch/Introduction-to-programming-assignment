package uk.ac.uos.i2p.s193805.taskhandling.task;

import org.junit.jupiter.api.Test;
import uk.ac.uos.i2p.s193805.parser.json.JSONParser;
import uk.ac.uos.i2p.s193805.parser.json.grammer.JsonObject;
import uk.ac.uos.i2p.s193805.taskhandling.task.builder.TasksBuilder;

import java.io.StringReader;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by IntelliJ IDEA.
 * User: finchaj
 * Date: 30/10/2018
 * Time: 10:48
 */

class TasksTest {

    @Test
    void testTasksCreation() throws Exception {


        Tasks tasks = TasksBuilder.buildTasksObject(new JSONParser(new StringReader("{\"id\": \"s113867\",\"tasks\": [\"/task/452359-4435382-6595137\",\"/task/99012-65325148-3574826\"]}\n")).parse());
        assertEquals("s113867", tasks.getId());
        assertEquals(2, tasks.getTaskURLS().size());
        assertEquals("/task/452359-4435382-6595137", tasks.getTaskURLS().get(0));
        assertEquals("/task/99012-65325148-3574826", tasks.getTaskURLS().get(1));
    }
}