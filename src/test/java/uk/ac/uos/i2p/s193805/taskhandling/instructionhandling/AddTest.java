package uk.ac.uos.i2p.s193805.taskhandling.instructionhandling;

import org.junit.jupiter.api.Test;
import uk.ac.uos.i2p.s193805.taskhandling.Result;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by IntelliJ IDEA.
 * User: finchaj
 * Date: 25/10/2018
 * Time: 10:19
 */

class AddTest {

    @Test
    void  testAdder() {

        Instruction adder = new Add();
        List<Object> valueList = Arrays.asList(50,50);
        int answer = adder.runInstruction(valueList).getAnswerIntValue();
        assertEquals( 100.00, answer , "Answer should be 100");
    }

    @Test
    void testAdder_With_Zero_As_Param() {

        Instruction adder = new Add();
        List<Object> valueList = Arrays.asList(0, 50);
        int answer = adder.runInstruction(valueList).getAnswerIntValue();
        assertEquals(50, answer, "Answer should be 50");
    }

    @Test
    void testAdder_With_All_Zeroes() {

        Instruction adder = new Add();
        List<Object> valueList = Arrays.asList(0, 0);
        int answer = adder.runInstruction(valueList).getAnswerIntValue();
        assertEquals(0, answer, "Answer should be 0");
    }

    @Test
    void testAdder_with_Only_Zero_Value() {

        Instruction adder = new Add();
        List<Object> valueList = Arrays.asList(0);
        int answer = adder.runInstruction(valueList).getAnswerIntValue();
        assertEquals(0, answer, "Answer should be 0");
    }

    @Test
    void testAdder_with_Only_One_Value() {

        Instruction adder = new Add();
        List<Object> valueList = Arrays.asList(5);
        int answer = adder.runInstruction(valueList).getAnswerIntValue();
        assertEquals(5, answer, "Answer should be 5");
    }

    @Test
    void testAdder_Text_Strings() {

        Instruction adder = new Add();
        List<Object> valueList = Arrays.asList("A", "B");

        try
        {
            adder.runInstruction(valueList);
            fail("Exception should have been thrown");
        } catch (IllegalArgumentException e)
        {
            assertEquals("Parameters must be Integer values only", e.getMessage());
        }

    }

    @Test
    void testAdder_Integer_And_Text_Strings() {

        Instruction adder = new Add();
        List<Object> valueList = Arrays.asList(1, "B");

        try
        {
            adder.runInstruction(valueList);
            fail("Exception should have been thrown");
        } catch (IllegalArgumentException e)
        {
            assertEquals("Parameters must be Integer values only", e.getMessage());
        }

    }

}