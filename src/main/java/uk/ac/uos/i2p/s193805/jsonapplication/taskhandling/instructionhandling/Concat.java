package uk.ac.uos.i2p.s193805.jsonapplication.taskhandling.instructionhandling;

import uk.ac.uos.i2p.s193805.jsonapplication.taskhandling.Result;

import java.util.List;

/**
 * Implementation of Concat Instruction
 */
public class Concat implements Instruction {

    /**
     * Concatenates supplied values to 1 string
     * @param values the values
     * @return Result object
     */
    @Override
    public Result runInstruction(List<String> values) {

        Result result = new Result();
        StringBuilder stringBuilder = new StringBuilder();

        if (values != null)//handle null list
        {
            for (String number : values)
            {
                stringBuilder.append(number);

            }

        }
        
        result.setAnswer(stringBuilder.toString());

        return result;
    }


}
