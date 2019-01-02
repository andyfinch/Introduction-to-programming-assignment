package uk.ac.uos.i2p.s193805.jsonapplication.taskhandling.task;

import org.junit.jupiter.api.Test;
import uk.ac.uos.i2p.s193805.jsonapplication.taskhandling.exceptions.InvalidInstructionException;
import uk.ac.uos.i2p.s193805.jsonapplication.taskhandling.task.builder.TaskBuilder;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by IntelliJ IDEA.
 * User: finchaj
 * Date: 25/10/2018
 * Time: 15:38
 */

class TaskTest {

    @Test
    public void testTaskBuilderAdd() throws IOException {

        Task task = new TaskBuilder().buildTaskObject("{\"instruction\": \"add\",\"parameters\": [\"23\",45],\"response URL\": \"/answer/d3ae45\"}", "");
        assertTrue( task instanceof AddTask );


    }

    @Test
    public void testTaskBuilderMultiply() throws IOException {

        Task task = new TaskBuilder().buildTaskObject("{\"instruction\": \"multiply\",\"parameters\": [\"23\",45],\"response URL\": \"/answer/d3ae45\"}", "");
        assertTrue(task instanceof MultiplyTask);


    }

    @Test
    public void testTaskBuilderConcat() throws IOException{

        Task task = new TaskBuilder().buildTaskObject("{\"instruction\": \"concat\",\"parameters\": [\"23\",45],\"response URL\": \"/answer/d3ae45\"}", "");
        assertTrue(task instanceof ConcatTask);


    }

    @Test
    public void testTaskBuilderInvalidInstruction() throws IOException{

        try
        {
            Task task = new TaskBuilder().buildTaskObject("{\"instruction\": \"divide\",\"parameters\": [\"23\",45],\"response URL\": \"/answer/d3ae45\"}", "");
            task.runInstruction();
            fail("Shouldn't get here");
        } catch (InvalidInstructionException e)
        {
            assertEquals("divide is not a valid Instruction", e.getMessage());
        }



    }



}