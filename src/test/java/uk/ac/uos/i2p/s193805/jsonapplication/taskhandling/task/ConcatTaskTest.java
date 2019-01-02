package uk.ac.uos.i2p.s193805.jsonapplication.taskhandling.task;

import org.junit.jupiter.api.Test;
import uk.ac.uos.i2p.s193805.jsonapplication.taskhandling.task.builder.TaskBuilder;

import java.io.IOException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Created by IntelliJ IDEA.
 * User: S193805
 * Date: 25/10/2018
 * Time: 15:38
 */

class ConcatTaskTest {

    @Test
    public void testTask()
    {
        Task task = new ConcatTask();
        task.setParamList(Arrays.asList("1","2","3"));
        task.runInstruction();
        assertEquals("123", task.result.getAnswer());



    }

    @Test
    public void testTaskBuilder() throws IOException {

        Task task = new TaskBuilder().buildTaskObject("{\"instruction\": \"concat\",\"parameters\": [\"23\",45],\"response URL\": \"/answer/d3ae45\"}", "");
        
        task.runInstruction();
        assertEquals("concat", task.getInstruction());
        assertEquals("23", task.getParamList().get(0));
        assertEquals("45", task.getParamList().get(1));
        assertEquals("2345", task.getResult().getAnswer());


    }

}