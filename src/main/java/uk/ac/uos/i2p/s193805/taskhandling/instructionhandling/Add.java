package uk.ac.uos.i2p.s193805.taskhandling.instructionhandling;

import uk.ac.uos.i2p.s193805.taskhandling.Result;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: finchaj
 * Date: 25/10/2018
 * Time: 09:43
 */

public class Add<T extends Integer> implements Instruction<T> {

    @Override
    @SuppressWarnings("unchecked")
    public Result<T> runInstruction(List<Object> values) throws IllegalArgumentException {
        Result<Integer> result = new Result<>();
        Integer runningSum = 0;

        if (values != null)
        {
            for (Object number : values)
            {
                try
                {
                    runningSum += Integer.parseInt(number.toString());
                } catch (NumberFormatException e)
                {
                    throw new IllegalArgumentException("Parameters must be Integer values only");
                }
            }
        }


        result.setAnswer(runningSum);

        return (Result<T>) result;
    }

    /*@Override
    public Result<T> runInstruction(List<T> values) {
        Result<T> result = new Result<>();
        Double runningSum = 0.00;

        if (values != null)
        {
            for (T value : values)
            {
                runningSum += Double.parseDouble(value.toString());
            }

            *//*for (Double number : values)
            {
                runningSum += number;
            }*//*
        }


        result.setAnswer((T) runningSum);

        return result;
    }*/

    /*@Override
    public Result<Double> runInstruction(List<Double> values) {
        Result<Double> result = new Result<>();
        Double runningSum = 0.00;

        if ( values != null )
        {
            for (Double number : values)
            {
                runningSum += number;
            }
        }


        result.setAnswer(runningSum);

        return result;
    }*/


}
