package uk.ac.uos.i2p.s193805.taskhandling.task.builder;

import uk.ac.uos.i2p.s193805.parser.JSONParser;
import uk.ac.uos.i2p.s193805.parser.JavaJSONObject;
import uk.ac.uos.i2p.s193805.taskhandling.task.Tasks;

/**
 * Created by IntelliJ IDEA.
 * User: finchaj
 * Date: 30/10/2018
 * Time: 10:57
 */

public class TasksBuilder {

    public static Tasks buildTasksObject(String json)
    {
        Tasks tasks = new Tasks();
        JavaJSONObject javaJSONObject = JSONParser.parseJSONtoJava(json);
        tasks.setId(javaJSONObject.getJSONStringValue("id"));
        tasks.setTaskURLS(javaJSONObject.getJSONStringArray("tasks"));

        return tasks;
    }

}
