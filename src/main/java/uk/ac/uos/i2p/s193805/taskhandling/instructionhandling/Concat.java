package uk.ac.uos.i2p.s193805.taskhandling.instructionhandling;

import uk.ac.uos.i2p.s193805.taskhandling.Result;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: finchaj
 * Date: 25/10/2018
 * Time: 09:43
 */

public class Concat<T extends String> implements Instruction<T> {
    @Override
    public Result<T> runInstruction(List<T> values) {
        Result<T> result = new Result<>();
        StringBuilder stringBuilder = new StringBuilder();

        if (values != null)//handle null list
        {
            for (Object number : values)
            {
                stringBuilder.append(number);

            }

        }

        result.setAnswer((T) stringBuilder.toString());

        return result;
    }

    /*@Override
    public Result<String> runInstruction(List<String> values) {
        Result<String> result = new Result<>();
        StringBuilder stringBuilder = new StringBuilder();

        if ( values != null )//handle null list
        {
            for (Object number : values)
            {
                stringBuilder.append(number);

            }

        }

        result.setAnswer(stringBuilder.toString());

        return result;
    }*/

}
