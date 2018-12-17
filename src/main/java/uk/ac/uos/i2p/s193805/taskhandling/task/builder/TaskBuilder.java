package uk.ac.uos.i2p.s193805.taskhandling.task.builder;

import uk.ac.uos.i2p.s193805.parser.json.JSONParser;

import uk.ac.uos.i2p.s193805.parser.json.grammer.JsonObject;
import uk.ac.uos.i2p.s193805.taskhandling.task.*;

import java.io.IOException;
import java.io.StringReader;

/**
 * Created by IntelliJ IDEA.
 * User: finchaj
 * Date: 30/10/2018
 * Time: 11:02
 */

public class TaskBuilder {

    public static Task buildTaskObject(String json, String requestURL) throws IllegalArgumentException, IOException {
        Task task = null;

        JsonObject jsonObject = new JSONParser(new StringReader(json)).parse();

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
            task = new InvalidTask();
        }



        task.setRequestURL(requestURL);
        task.setJson(json);
        task.setInstruction(instruction);
        task.setParamList(jsonObject.getJsonArray("parameters").getStringList());
        task.setResponseURL(jsonObject.getString("response URL"));

        return task;
    }
}
