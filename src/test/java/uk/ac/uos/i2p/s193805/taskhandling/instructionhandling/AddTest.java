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
    void runInstruction() {

        Instruction<Double> adder = new Add();
        List<Double> valueList = Arrays.asList(50.00,50.00);
        Result<Double> result = adder.runInstruction(valueList);
        assertEquals( 100.00, result.getAnswer().doubleValue() , "Answer should be 100");
    }
}