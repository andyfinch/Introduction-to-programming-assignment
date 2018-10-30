package uk.ac.uos.i2p.s193805.taskhandling.task.builder;

import uk.ac.uos.i2p.s193805.parser.JSONParser;
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
        JSONParser jsonParser = new JSONParser(json);
        tasks.setId(jsonParser.getJSONStringValue("id"));
        tasks.setTaskURLS(jsonParser.getJSONStringArray("tasks"));

        return tasks;
    }

}
