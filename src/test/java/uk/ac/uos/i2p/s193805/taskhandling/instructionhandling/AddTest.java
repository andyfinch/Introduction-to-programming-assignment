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
    void  runInstruction() {

        Instruction<Double> adder = new Add<>();
        List<Double> valueList = Arrays.asList(50.00,50.00);
        double answer = adder.runInstruction(valueList).getAnswer();
        assertEquals( 100.00, answer , "Answer should be 100");
    }

    @Test
    void runInstruction2() {

        Instruction<Double> adder = new Add<>();
        List<Double> valueList = Arrays.asList(0.00, 50.00);
        double answer = adder.runInstruction(valueList).getAnswer();
        assertEquals(50.00, answer, "Answer should be 50");
    }

    @Test
    void runInstruction3() {

        Instruction<Double> adder = new Add<>();
        List<Double> valueList = Arrays.asList(0.00, 0.00);
        double answer = adder.runInstruction(valueList).getAnswer();
        assertEquals(0.00, answer, "Answer should be 0");
    }

    @Test
    void runInstruction4() {

        Instruction<Double> adder = new Add<>();
        List<Double> valueList = Arrays.asList(0.00);
        double answer = adder.runInstruction(valueList).getAnswer();
        assertEquals(0.00, answer, "Answer should be 0");
    }

   /* @Test
    void runInstruction2() {

        Instruction<String> adder = new Concat();
        List<Double> valueList = Arrays.asList(50.00, 50.00);
        Result<Double> result = adder.runInstruction(valueList);
        assertEquals(100.00, result.getAnswer().doubleValue(), "Answer should be 100");
    }*/
}