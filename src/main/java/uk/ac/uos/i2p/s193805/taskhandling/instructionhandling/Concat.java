package uk.ac.uos.i2p.s193805.taskhandling.instructionhandling;

import uk.ac.uos.i2p.s193805.taskhandling.Result;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: finchaj
 * Date: 25/10/2018
 * Time: 09:43
 */

public class Concat implements Instruction {

    @Override
    @SuppressWarnings("unchecked")
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
