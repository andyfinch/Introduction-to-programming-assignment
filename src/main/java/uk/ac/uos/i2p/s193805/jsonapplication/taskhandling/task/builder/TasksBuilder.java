package uk.ac.uos.i2p.s193805.jsonapplication.taskhandling.task.builder;

import uk.ac.uos.i2p.s193805.jsonapplication.taskhandling.task.Tasks;
import uk.ac.uos.i2p.s193805.parser.json.grammer.JsonObject;

import java.io.IOException;

/**
 * Helper class to simplify building a Tasks object
 */
public class TasksBuilder {

    /**
     * Build tasks object tasks.
     *
     * @param jsonObject the json object
     * @return the tasks
     */
    public static Tasks buildTasksObject(JsonObject jsonObject)
    {
        Tasks tasks = new Tasks();
        tasks.setId(jsonObject.getString("id"));
        tasks.setTaskURLS(jsonObject.getJsonArrayStringList("tasks"));

        return tasks;
    }

}
