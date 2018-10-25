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

class MultiplyTest {

    @Test
    void runInstruction() {

        Instruction<Double> multiplier = new Multiply();
        List<Double> valueList = Arrays.asList(2.0 , 10.00);
        Result<Double> result = multiplier.runInstruction(valueList);
        assertEquals( 20.00, result.getAnswer().doubleValue() , "Answer should be 20");
    }

    @Test
    void runInstruction2() {

        Instruction<Double> multiplier = new Multiply();
        List<Double> valueList = Arrays.asList(2.5, 10.5);
        Result<Double> result = multiplier.runInstruction(valueList);
        assertEquals(26.25, result.getAnswer().doubleValue(), "Answer should be 26.25");
    }
}