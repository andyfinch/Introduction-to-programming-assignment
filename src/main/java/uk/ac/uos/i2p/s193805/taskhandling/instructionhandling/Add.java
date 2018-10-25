package uk.ac.uos.i2p.s193805.taskhandling.instructionhandling;

import uk.ac.uos.i2p.s193805.taskhandling.Result;

/**
 * Created by IntelliJ IDEA.
 * User: finchaj
 * Date: 25/10/2018
 * Time: 09:43
 */

public class Add implements Instruction {

    public Result runInstruction(String... params) {

        Result result = new Result();
        Double runningSum = 0.00;

        for (String number : params)
        {
            try
            {
                runningSum += Double.parseDouble(number);
            } catch (NumberFormatException e)
            {
                throw new IllegalArgumentException("Only Integers can be added together. Invalid value=" + number);
            }
        }

        result.setAnswer(runningSum.toString());

        return result;

    }
}
