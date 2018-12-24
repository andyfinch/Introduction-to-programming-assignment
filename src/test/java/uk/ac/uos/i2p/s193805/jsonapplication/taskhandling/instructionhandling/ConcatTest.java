package uk.ac.uos.i2p.s193805.jsonapplication.taskhandling.instructionhandling;

import org.junit.jupiter.api.Test;
import uk.ac.uos.i2p.s193805.jsonapplication.taskhandling.Result;

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
    void testConcate_With_Integers() {

        Instruction concatter = new Concat();
        List<String> valueList = Arrays.asList("1", "2", "10");
        Result result = concatter.runInstruction(valueList);//warning ok as this can take any object to concat
        assertEquals( "1210", result.getAnswer() , "Answer should be 1210");
    }

    @Test
    void runInstruction_With_Text_Strings() {

        Instruction concatter = new Concat();
        List<String> valueList = Arrays.asList("A", "B", "C");
        Result result = concatter.runInstruction(valueList);//warning ok as this can take any object to concat
        assertEquals("ABC", result.getAnswer(), "Answer should be ABC");
    }

    @Test
    void runInstruction_With_Integer_And_Text_Strings() {

        Instruction concatter = new Concat();
        List<String> valueList = Arrays.asList("A", "B", "1", "2");
        Result result = concatter.runInstruction(valueList);//warning ok as this can take any object to concat
        assertEquals("AB12", result.getAnswer(), "Answer should be AB12");
    }

    @Test
    void runInstruction_With_Integer_Boolean_And_Text_Strings() {

        Instruction concatter = new Concat();
        List<String> valueList = Arrays.asList("A", "B", "1", "2", "true");
        Result result = concatter.runInstruction(valueList);//warning ok as this can take any object to concat
        assertEquals("AB12true", result.getAnswer(), "Answer should be AB12true");
    }

    @Test
    void runInstructionEmptyList() {

        Instruction concatter = new Concat();
        List<String>valueList = Arrays.asList();
        Result result = concatter.runInstruction(valueList);//warning ok as this can take any object to concat
        assertEquals("", result.getAnswer(), "Answer should be empty");
    }

    @Test
    void runInstructionNullList() {

        Instruction concatter = new Concat();
        List<String>valueList = null;
        Result result = concatter.runInstruction(valueList);//warning ok as this can take any object to concat
        assertEquals("", result.getAnswer(), "Answer should be empty");
    }
}