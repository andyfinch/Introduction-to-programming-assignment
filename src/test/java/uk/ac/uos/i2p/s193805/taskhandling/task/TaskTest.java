package uk.ac.uos.i2p.s193805.taskhandling.task;

import org.junit.jupiter.api.Test;
import uk.ac.uos.i2p.s193805.taskhandling.instructionhandling.Add;
import uk.ac.uos.i2p.s193805.taskhandling.instructionhandling.Instruction;
import uk.ac.uos.i2p.s193805.taskhandling.task.Task;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by IntelliJ IDEA.
 * User: finchaj
 * Date: 25/10/2018
 * Time: 15:38
 */

class TaskTest {

    @Test
    public void testTest()
    {
        Task<Integer> task = new AddTask<>();
        task.setInstruction("add");
        task.setParamList(Arrays.asList(1,2,3));
        task.runInstruction();
        assertEquals(6, task.result.getAnswer().intValue());

    }


    @Test
    public void testTest2() {
        Task<Integer> task = new AddTask<>();
        task.setInstruction("add");
        task.setParamList(Arrays.asList(1, 2, 3));
        task.runInstruction();
        assertEquals(6, task.result.getAnswer().intValue());
    }

}