package uk.ac.uos.i2p.s193805.taskhandling.task.builder;

import uk.ac.uos.i2p.s193805.parser.JSONParser;
import uk.ac.uos.i2p.s193805.taskhandling.task.AddTask;
import uk.ac.uos.i2p.s193805.taskhandling.task.ConcatTask;
import uk.ac.uos.i2p.s193805.taskhandling.task.MultiplyTask;
import uk.ac.uos.i2p.s193805.taskhandling.task.Task;

/**
 * Created by IntelliJ IDEA.
 * User: finchaj
 * Date: 30/10/2018
 * Time: 11:02
 */

public class TaskBuilder {

    public static Task buildTaskObject(String json) throws IllegalArgumentException {
        Task task = null;
        JSONParser jsonParser = new JSONParser(json);

        if ( jsonParser.getJSONStringValue("instruction").equalsIgnoreCase("add"))
        {
           task = new AddTask();
        }
        else if (jsonParser.getJSONStringValue("instruction").equalsIgnoreCase("multiply"))
        {
           task = new MultiplyTask();
        }
        else if (jsonParser.getJSONStringValue("instruction").equalsIgnoreCase("concat"))
        {
            task = new ConcatTask();
        }
        else
        {
            throw new IllegalArgumentException("Invalid Instruction " + jsonParser.getJSONStringValue("instruction"));
        }


        task.setInstruction(jsonParser.getJSONStringValue("instruction"));
        task.setParamList(jsonParser.getJSONArray("parameters"));
        task.setResponseURL(jsonParser.getJSONStringValue("response URL"));

        return task;
    }
}
