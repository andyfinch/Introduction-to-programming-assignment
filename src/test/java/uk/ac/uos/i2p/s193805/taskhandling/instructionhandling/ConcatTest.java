package uk.ac.uos.i2p.s193805.taskhandling.instructionhandling;

import org.junit.jupiter.api.Test;
import uk.ac.uos.i2p.s193805.taskhandling.Result;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Created by IntelliJ IDEA.
 * User: finchaj
 * Date: 25/10/2018
 * Time: 10:19
 */

class ConcatTest {

    @Test
    void runInstruction() {

        Instruction<String> concatter = new Concat<>();
        List<String> valueList = Arrays.asList("A", "2", "10");
        Result<String> result = concatter.runInstruction(valueList);//warning ok as this can take any object to concat
        assertEquals( "A210", result.getAnswer() , "Answer should be 20");
    }

    @Test
    void runInstruction2() {

        Instruction<String> concatter = new Concat<>();
        List<String> valueList = Arrays.asList("A", "2", "10", "10.50");
        Result<String> result = concatter.runInstruction(valueList);//warning ok as this can take any object to concat
        assertEquals("A21010.50", result.getAnswer(), "Answer should be 20");
    }

    @Test
    void runInstructionEmptyList() {

        Instruction<String> concatter = new Concat<>();
        List<String>valueList = Arrays.asList();
        Result<String> result = concatter.runInstruction(valueList);//warning ok as this can take any object to concat
        assertEquals("", result.getAnswer(), "Answer should be empty");
    }

    @Test
    void runInstructionNullList() {

        Instruction<String> concatter = new Concat<>();
        List<String>valueList = null;
        Result<String> result = concatter.runInstruction(valueList);//warning ok as this can take any object to concat
        assertEquals("", result.getAnswer(), "Answer should be empty");
    }
}