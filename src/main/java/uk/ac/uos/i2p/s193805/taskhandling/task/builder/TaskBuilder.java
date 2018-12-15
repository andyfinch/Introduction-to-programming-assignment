package uk.ac.uos.i2p.s193805.taskhandling.task.builder;

import uk.ac.uos.i2p.s193805.parser.json.JSONParser;

import uk.ac.uos.i2p.s193805.parser.json.grammer.JsonObject;
import uk.ac.uos.i2p.s193805.taskhandling.task.AddTask;
import uk.ac.uos.i2p.s193805.taskhandling.task.ConcatTask;
import uk.ac.uos.i2p.s193805.taskhandling.task.MultiplyTask;
import uk.ac.uos.i2p.s193805.taskhandling.task.Task;

import java.io.IOException;
import java.io.StringReader;

/**
 * Created by IntelliJ IDEA.
 * User: finchaj
 * Date: 30/10/2018
 * Time: 11:02
 */

public class TaskBuilder {

    public static Task buildTaskObject(JsonObject jsonObject) throws IllegalArgumentException, IOException {
        Task task = null;

        String instruction = jsonObject.getString("instruction");


        if ("add".equalsIgnoreCase(instruction))
        {
           task = new AddTask();
        }
        else if ("multiply".equalsIgnoreCase(instruction))
        {
           task = new MultiplyTask();
        }
        else if ("concat".equalsIgnoreCase(instruction))
        {
            task = new ConcatTask();
        }
        else
        {
            throw new IllegalArgumentException("Invalid Instruction " + jsonObject.getJSONString("instruction"));
        }



        task.setInstruction(instruction);
        task.setParamList(jsonObject.getJsonArray("parameters").getStringList());
        task.setResponseURL(jsonObject.getString("response URL"));

        return task;
    }
}
