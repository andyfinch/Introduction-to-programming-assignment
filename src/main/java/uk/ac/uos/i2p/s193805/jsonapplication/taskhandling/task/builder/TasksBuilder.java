package uk.ac.uos.i2p.s193805.jsonapplication.taskhandling.task.builder;

import uk.ac.uos.i2p.s193805.parser.json.grammer.JsonObject;
import uk.ac.uos.i2p.s193805.jsonapplication.taskhandling.task.Tasks;

import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: finchaj
 * Date: 30/10/2018
 * Time: 10:57
 */

public class TasksBuilder {

    public static Tasks buildTasksObject(JsonObject jsonObject) throws IOException
    {
        Tasks tasks = new Tasks();
        tasks.setId(jsonObject.getString("id"));
        tasks.setTaskURLS(jsonObject.getJsonArrayStringList("tasks"));

        return tasks;
    }

}