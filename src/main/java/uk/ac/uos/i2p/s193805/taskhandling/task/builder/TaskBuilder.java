package uk.ac.uos.i2p.s193805.taskhandling.task.builder;

import uk.ac.uos.i2p.s193805.http.HttpResponseVO;
import uk.ac.uos.i2p.s193805.parser.exceptions.JsonParseException;
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

    /*public Task buildTaskObject(HttpResponseVO httpResponseVO) throws IllegalArgumentException, IOException
    {
        return buildTaskObject(httpResponseVO.getBody(), httpResponseVO.getRequestURL());
    }*/

    public Task buildTaskObject(String json, JsonObject jsonObject, String requestURL) throws IllegalArgumentException, IOException {
        Task task = null;
        String instruction = "";
        instruction = jsonObject.getString("instruction");

        if (instruction.equalsIgnoreCase("add"))
        {
            task = new AddTask();
        }
        else if (instruction.equalsIgnoreCase("multiply"))
        {
            task = new MultiplyTask();
        }
        else if (instruction.equalsIgnoreCase("concat"))
        {
            task = new ConcatTask();
        }
        else
        {
            task = new Task();
        }

        task.setParamList(jsonObject.getJsonArray("parameters").getStringList());
        task.setResponseURL(jsonObject.getString("response URL"));


        task.setRequestURL(requestURL);
        task.setJson(json);
        task.setInstruction(instruction);


        return task;
    }


    public Task buildTaskObject(String json, String requestURL) throws IllegalArgumentException, IOException {
        Task task;
        JsonObject jsonObject;

        try
        {
            jsonObject = new JSONParser(new StringReader(json)).parse();
            return this.buildTaskObject(json, jsonObject, requestURL);



        } catch (JsonParseException e)
        {
            e.printStackTrace();
            task = new Task();
            task.setRequestURL(requestURL);
            task.setJson(json);
            return task;

        }

    }
}
