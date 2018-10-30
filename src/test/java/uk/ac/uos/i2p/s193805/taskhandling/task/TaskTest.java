package uk.ac.uos.i2p.s193805.taskhandling.task;

import org.junit.jupiter.api.Test;
import uk.ac.uos.i2p.s193805.taskhandling.task.builder.TaskBuilder;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by IntelliJ IDEA.
 * User: finchaj
 * Date: 25/10/2018
 * Time: 15:38
 */

class TaskTest {

    @Test
    public void testTaskBuilderAdd() {

        Task task = TaskBuilder.buildTaskObject("{\"instruction\": \"add\",\"parameters\": [\"23\",45],\"response URL\": \"/answer/d3ae45\"}");
        assertTrue( task instanceof AddTask );


    }

    @Test
    public void testTaskBuilderMultiply() {

        Task task = TaskBuilder.buildTaskObject("{\"instruction\": \"multiply\",\"parameters\": [\"23\",45],\"response URL\": \"/answer/d3ae45\"}");
        assertTrue(task instanceof MultiplyTask);


    }

    @Test
    public void testTaskBuilderConcat() {

        Task task = TaskBuilder.buildTaskObject("{\"instruction\": \"concat\",\"parameters\": [\"23\",45],\"response URL\": \"/answer/d3ae45\"}");
        assertTrue(task instanceof ConcatTask);


    }

    @Test
    public void testTaskBuilderInvalidInstruction() {

        try
        {
            TaskBuilder.buildTaskObject("{\"instruction\": \"divide\",\"parameters\": [\"23\",45],\"response URL\": \"/answer/d3ae45\"}");
            fail("Shouldn't get here");
        } catch (IllegalArgumentException e)
        {
            assertEquals("Invalid Instruction divide", e.getMessage());
        }



    }

}