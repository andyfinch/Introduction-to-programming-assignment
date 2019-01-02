package uk.ac.uos.i2p.s193805.jsonapplication.taskhandling.instructionhandling;

import uk.ac.uos.i2p.s193805.jsonapplication.taskhandling.Result;

import java.util.List;

/**
 * Implementation of Multiply Instruction
 */
public class Multiply implements Instruction {

    /**
     * Implements Run instruction to Multiply values in supplied List
     * @param values the values
     * @return Result Object
     * @throws IllegalArgumentException
     */
    @Override
    public Result runInstruction(List<String> values) throws IllegalArgumentException {

        Result result = new Result();
        int runningSum = 1;

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


        result.setAnswer(String.valueOf(runningSum));

        return result;
    }


}
