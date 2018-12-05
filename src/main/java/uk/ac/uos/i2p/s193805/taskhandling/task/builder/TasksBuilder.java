package uk.ac.uos.i2p.s193805.taskhandling.task.builder;

import uk.ac.uos.i2p.s193805.parser.json.JSONParser;
import uk.ac.uos.i2p.s193805.parser.JavaJSONObject;
import uk.ac.uos.i2p.s193805.parser.json.grammer.JsonObject;
import uk.ac.uos.i2p.s193805.taskhandling.task.Tasks;

import java.io.IOException;
import java.io.StringReader;

/**
 * Created by IntelliJ IDEA.
 * User: finchaj
 * Date: 30/10/2018
 * Time: 10:57
 */

public class TasksBuilder {

    public static Tasks buildTasksObject(String json) throws IOException
    {
        Tasks tasks = new Tasks();
        JsonObject jsonObject = new JSONParser().parse(new StringReader(json));
        tasks.setId(jsonObject.getJSONString("id"));
        tasks.setTaskURLS(jsonObject.getJsonArrayStringList("tasks"));

        return tasks;
    }

}
