package uk.ac.uos.i2p.s193805.taskhandling.instructionhandling;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by IntelliJ IDEA.
 * User: finchaj
 * Date: 25/10/2018
 * Time: 10:19
 */

class MultiplyTest {

    @Test
    void testMultiplyer_2_Integers() {

        Instruction multiplier = new Multiply();
        List<Object> valueList = Arrays.asList(2 , 10);
        int answer = multiplier.runInstruction(valueList).getAnswerIntValue();
        assertEquals( 20, answer , "Answer should be 20");
    }

    @Test
    void testMultiplyer_3_Integers() {

        Instruction multiplier = new Multiply();
        List<Object> valueList = Arrays.asList(2, 2, 2);
        int answer = multiplier.runInstruction(valueList).getAnswerIntValue();
        assertEquals(8, answer, "Answer should be 8");
    }

    @Test
    void testMultiplyer_6_Integers() {

        Instruction multiplier = new Multiply();
        List<Object> valueList = Arrays.asList(1, 2, 3, 4, 5, 6);
        int answer = multiplier.runInstruction(valueList).getAnswerIntValue();
        assertEquals(720, answer, "Answer should be 720.00");
    }

    @Test
    void testMultiplyer_With_A_Zero_Integer() {

        Instruction multiplier = new Multiply();
        List<Object> valueList = Arrays.asList(0, 2, 3, 4, 5, 6);
        int answer = multiplier.runInstruction(valueList).getAnswerIntValue();
        assertEquals(0, answer, "Answer should be 0");
    }

    @Test
    void testMultiplyer_With_Text_Strings() {

        Instruction multiplier = new Multiply();
        List<Object> valueList = Arrays.asList("A", "B");
        try
        {
            multiplier.runInstruction(valueList);
            fail("Exception should have been thrown");
        } catch (IllegalArgumentException e)
        {
            assertEquals("Parameters must be Integer values only", e.getMessage());
        }
    }

    @Test
    void testMultiplyer_With_Integer_And_Text_Strings() {

        Instruction multiplier = new Multiply();
        List<Object> valueList = Arrays.asList(1, 2, "A", "B");
        try
        {
            multiplier.runInstruction(valueList);
            fail("Exception should have been thrown");
        } catch (IllegalArgumentException e)
        {
            assertEquals("Parameters must be Integer values only", e.getMessage());
        }
    }

    /*try
        {
            adder.runInstruction(valueList).getAnswer();
            fail("Exception should have been thrown");
        } catch (IllegalArgumentException e)
        {
            assertEquals("Parameters must be Integer values only", e.getMessage());
        }
*/
}