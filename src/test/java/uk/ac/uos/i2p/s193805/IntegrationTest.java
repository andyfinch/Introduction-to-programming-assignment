package uk.ac.uos.i2p.s193805;

import org.junit.jupiter.api.Test;
import uk.ac.uos.i2p.s193805.http.HTTPRequest;
import uk.ac.uos.i2p.s193805.taskhandling.task.Task;
import uk.ac.uos.i2p.s193805.taskhandling.task.Tasks;
import uk.ac.uos.i2p.s193805.taskhandling.task.builder.TaskBuilder;
import uk.ac.uos.i2p.s193805.taskhandling.task.builder.TasksBuilder;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by IntelliJ IDEA.
 * User: finchaj
 * Date: 04/12/2018
 * Time: 13:17
 */

public class IntegrationTest {

    public final static String baseurl = "http://i2j.openode.io";
    public final static String studentQuery = "/student?id=s193805";


    @Test
    void testSendCorrectResults() {

        HTTPRequest httpRequest = new HTTPRequest();

        try
        {
            httpRequest.sendHTTPRequest(baseurl+studentQuery, null, "GET");
            Tasks tasks = TasksBuilder.buildTasksObject(httpRequest.getBody());

            for (String taskURL : tasks.getTaskURLS())
            {
                httpRequest.sendHTTPGetRequest(baseurl+taskURL);
                Task task = null;
                try
                {
                    task = TaskBuilder.buildTaskObject(httpRequest.getBody());
                } catch (RuntimeException e )
                {
                    httpRequest.sendHTTPPostRequest(baseurl + taskURL, "text/plain", "Error" + e.getMessage());
                    assertEquals(200, httpRequest.getResponse());
                    continue;
                }

                System.out.println(task.getInstruction());
                System.out.println(task.getParamList());
                try
                {
                    task.runInstruction();
                    httpRequest.sendHTTPPostRequest(baseurl+task.getResponseURL(), "text/plain", task.getResult().getAnswer());
                    assertEquals(200, httpRequest.getResponse());
                } catch (IllegalArgumentException e)
                {
                    e.printStackTrace();
                }

                System.out.println(task.getResult().getAnswer());
            }

        } catch (Exception e)
        {
            e.printStackTrace();
        }


    }

    @Test
    void testSendIncorrectResults() {

        HTTPRequest httpRequest = new HTTPRequest();

        try
        {
            httpRequest.sendHTTPRequest(baseurl + studentQuery, null, "GET");
            Tasks tasks = TasksBuilder.buildTasksObject(httpRequest.getBody());

            for (String taskURL : tasks.getTaskURLS())
            {
                httpRequest.sendHTTPGetRequest(baseurl + taskURL);
                Task task = null;
                try
                {
                    task = TaskBuilder.buildTaskObject(httpRequest.getBody());
                } catch (RuntimeException e)
                {
                    httpRequest.sendHTTPPostRequest(baseurl + taskURL, "text/plain", "Error" + e.getMessage());
                    assertEquals(200, httpRequest.getResponse());
                    continue;
                }
                System.out.println(taskURL);
                System.out.println(task.getResponseURL());
                System.out.println(task.getInstruction());
                System.out.println(task.getParamList());
                try
                {
                    task.runInstruction();
                    httpRequest.sendHTTPPostRequest(baseurl + task.getResponseURL(), "text/plain", task.getResult().getAnswer()+"Wrong");
                    assertEquals(400, httpRequest.getResponse());
                } catch (IllegalArgumentException e)
                {
                    e.printStackTrace();
                }

                System.out.println(task.getResult().getAnswer());
            }

        } catch (Exception e)
        {
            e.printStackTrace();
        }


    }
}
