package uk.ac.uos.i2p.s193805.taskhandling.instructionhandling;

import uk.ac.uos.i2p.s193805.taskhandling.Result;

/**
 * Created by IntelliJ IDEA.
 * User: finchaj
 * Date: 25/10/2018
 * Time: 09:43
 */

public class Concat implements Instruction {

    public Result runInstruction(String... params) {

        Result result = new Result();
        StringBuilder stringBuilder = new StringBuilder();

        for (String number : params)
        {
            stringBuilder.append(number);

        }

        result.setAnswer(stringBuilder.toString());

        return result;

    }
}
