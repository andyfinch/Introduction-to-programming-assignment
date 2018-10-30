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
    void testConcate_With_Integers() {

        Instruction<String> concatter = new Concat<>();
        List<Object> valueList = Arrays.asList(1, 2, 10);
        Result<String> result = concatter.runInstruction(valueList);//warning ok as this can take any object to concat
        assertEquals( "1210", result.getAnswer() , "Answer should be 1210");
    }

    @Test
    void runInstruction_With_Text_Strings() {

        Instruction<String> concatter = new Concat<>();
        List<Object> valueList = Arrays.asList("A", "B", "C");
        Result<String> result = concatter.runInstruction(valueList);//warning ok as this can take any object to concat
        assertEquals("ABC", result.getAnswer(), "Answer should be ABC");
    }

    @Test
    void runInstruction_With_Integer_And_Text_Strings() {

        Instruction<String> concatter = new Concat<>();
        List<Object> valueList = Arrays.asList("A", "B", 1, 2);
        Result<String> result = concatter.runInstruction(valueList);//warning ok as this can take any object to concat
        assertEquals("AB12", result.getAnswer(), "Answer should be AB12");
    }

    @Test
    void runInstruction_With_Integer_Boolean_And_Text_Strings() {

        Instruction<String> concatter = new Concat<>();
        List<Object> valueList = Arrays.asList("A", "B", 1, 2, true);
        Result<String> result = concatter.runInstruction(valueList);//warning ok as this can take any object to concat
        assertEquals("AB12true", result.getAnswer(), "Answer should be AB12true");
    }

    @Test
    void runInstructionEmptyList() {

        Instruction<String> concatter = new Concat<>();
        List<Object>valueList = Arrays.asList();
        Result<String> result = concatter.runInstruction(valueList);//warning ok as this can take any object to concat
        assertEquals("", result.getAnswer(), "Answer should be empty");
    }

    @Test
    void runInstructionNullList() {

        Instruction<String> concatter = new Concat<>();
        List<Object>valueList = null;
        Result<String> result = concatter.runInstruction(valueList);//warning ok as this can take any object to concat
        assertEquals("", result.getAnswer(), "Answer should be empty");
    }
}