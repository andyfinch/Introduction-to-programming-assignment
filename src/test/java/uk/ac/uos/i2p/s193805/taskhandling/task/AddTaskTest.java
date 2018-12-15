package uk.ac.uos.i2p.s193805.taskhandling.task;

import org.junit.jupiter.api.Test;
import uk.ac.uos.i2p.s193805.parser.json.JSONParser;
import uk.ac.uos.i2p.s193805.parser.json.grammer.JsonObject;
import uk.ac.uos.i2p.s193805.taskhandling.task.builder.TaskBuilder;
import uk.ac.uos.i2p.s193805.taskhandling.task.builder.TasksBuilder;

import java.io.IOException;
import java.io.StringReader;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Created by IntelliJ IDEA.
 * User: finchaj
 * Date: 25/10/2018
 * Time: 15:38
 */

class AddTaskTest {

    @Test
    public void testTask()
    {
        Task task = new AddTask();
        task.setInstruction("add");
        task.setParamList(Arrays.asList("1","2","3"));
        task.runInstruction();
        assertEquals(6, task.result.getAnswerIntValue());



    }

    @Test
    public void testTaskBuilder() throws IOException {

        JsonObject jsonObject = new JSONParser(new StringReader("{\"instruction\": \"add\",\"parameters\": [\"23\",45],\"response URL\": \"/answer/d3ae45\"}")).parse();
        Task task = TaskBuilder.buildTaskObject(jsonObject);

        //Task<Integer> task = new AddTask<>();
        task.runInstruction();
        assertEquals("add", task.getInstruction());
        assertEquals("23", task.getParamList().get(0));
        assertEquals("45", task.getParamList().get(1));
        assertEquals(68, task.getResult().getAnswerIntValue());


    }

}