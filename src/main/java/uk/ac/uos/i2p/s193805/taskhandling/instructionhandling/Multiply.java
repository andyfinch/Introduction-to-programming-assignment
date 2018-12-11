package uk.ac.uos.i2p.s193805.taskhandling.instructionhandling;

import uk.ac.uos.i2p.s193805.taskhandling.Result;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: finchaj
 * Date: 25/10/2018
 * Time: 09:43
 */

public class Multiply implements Instruction {

    @Override
    @SuppressWarnings("unchecked")
    public Result runInstruction(List<String> values) throws IllegalArgumentException {

        Result result = new Result();
        Integer runningSum = 1;

        if (values != null)
        {
            for (String number : values)
            {
                try
                {
                    runningSum *= Integer.parseInt(number);
                } catch (NumberFormatException e)
                {
                    throw new IllegalArgumentException("Parameters must be Integer values only");
                }
            }
        }


        result.setAnswer(runningSum.toString());

        return result;
    }


}
