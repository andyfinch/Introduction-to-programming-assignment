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

        Instruction<Integer> adder = new Add<>();
        List<Object> valueList = Arrays.asList(50,50);
        double answer = adder.runInstruction(valueList).getAnswer();
        assertEquals( 100.00, answer , "Answer should be 100");
    }

    @Test
    void testAdder_With_Zero_As_Param() {

        Instruction<Integer> adder = new Add<>();
        List<Object> valueList = Arrays.asList(0, 50);
        double answer = adder.runInstruction(valueList).getAnswer();
        assertEquals(50, answer, "Answer should be 50");
    }

    @Test
    void testAdder_With_All_Zeroes() {

        Instruction<Integer> adder = new Add<>();
        List<Object> valueList = Arrays.asList(0, 0);
        double answer = adder.runInstruction(valueList).getAnswer();
        assertEquals(0, answer, "Answer should be 0");
    }

    @Test
    void testAdder_with_Only_Zero_Value() {

        Instruction<Integer> adder = new Add<>();
        List<Object> valueList = Arrays.asList(0);
        double answer = adder.runInstruction(valueList).getAnswer();
        assertEquals(0, answer, "Answer should be 0");
    }

    @Test
    void testAdder_with_Only_One_Value() {

        Instruction<Integer> adder = new Add<>();
        List<Object> valueList = Arrays.asList(5);
        double answer = adder.runInstruction(valueList).getAnswer();
        assertEquals(5, answer, "Answer should be 5");
    }

    @Test
    void testAdder_Text_Strings() {

        Instruction<Integer> adder = new Add<>();
        List<Object> valueList = Arrays.asList("A", "B");

        try
        {
            adder.runInstruction(valueList).getAnswer();
            fail("Exception should have been thrown");
        } catch (IllegalArgumentException e)
        {
            assertEquals("Parameters must be Integer values only", e.getMessage());
        }

    }

    @Test
    void testAdder_Integer_And_Text_Strings() {

        Instruction<Integer> adder = new Add<>();
        List<Object> valueList = Arrays.asList(1, "B");

        try
        {
            adder.runInstruction(valueList).getAnswer();
            fail("Exception should have been thrown");
        } catch (IllegalArgumentException e)
        {
            assertEquals("Parameters must be Integer values only", e.getMessage());
        }

    }

}