package uk.ac.uos.i2p.s193805.taskhandling.task.builder;

import uk.ac.uos.i2p.s193805.parser.JSONParser;
import uk.ac.uos.i2p.s193805.parser.JavaJSONObject;
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
        JavaJSONObject javaJSONObject = JSONParser.parseJSONtoJava(json);

        if (javaJSONObject.getJSONStringValue("instruction").equalsIgnoreCase("add"))
        {
           task = new AddTask();
        }
        else if (javaJSONObject.getJSONStringValue("instruction").equalsIgnoreCase("multiply"))
        {
           task = new MultiplyTask();
        }
        else if (javaJSONObject.getJSONStringValue("instruction").equalsIgnoreCase("concat"))
        {
            task = new ConcatTask();
        }
        else
        {
            throw new IllegalArgumentException("Invalid Instruction " + javaJSONObject.getJSONStringValue("instruction"));
        }


        task.setInstruction(javaJSONObject.getJSONStringValue("instruction"));
        task.setParamList(javaJSONObject.getJSONArray("parameters"));
        task.setResponseURL(javaJSONObject.getJSONStringValue("response URL"));

        return task;
    }
}
