package uk.ac.uos.i2p.s193805.taskhandling.instructionhandling;

import uk.ac.uos.i2p.s193805.taskhandling.Result;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: finchaj
 * Date: 25/10/2018
 * Time: 09:43
 */

public class Multiply implements Instruction<Double> {

    @Override
    public Result<Double> runInstruction(List<Double> values) {
        Result<Double> result = new Result<>();
        Double runningSum = 1.00;

        if ( values != null )
        {
            for (Double number : values)
            {
                runningSum *= number;
            }
        }


        result.setAnswer(runningSum);

        return result;
    }

}
