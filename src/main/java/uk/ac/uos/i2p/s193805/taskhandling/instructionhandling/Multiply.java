package uk.ac.uos.i2p.s193805.taskhandling.instructionhandling;

import uk.ac.uos.i2p.s193805.taskhandling.Result;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: finchaj
 * Date: 25/10/2018
 * Time: 09:43
 */

public class Multiply<T extends Number> implements Instruction<T> {

    @Override
    public Result<T> runInstruction(List<T> values) {
        Result<Double> result = new Result<>();
        Double runningSum = 1.00;

        if (values != null)
        {
            for (T number : values)
            {
                runningSum *= Double.parseDouble(number.toString());
            }
        }


        result.setAnswer(runningSum);

        return (Result<T>) result;
    }


}
