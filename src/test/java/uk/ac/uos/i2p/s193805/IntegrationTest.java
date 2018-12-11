package uk.ac.uos.i2p.s193805;

import org.junit.jupiter.api.Test;
import uk.ac.uos.i2p.s193805.http.HttpRequester;
import uk.ac.uos.i2p.s193805.http.HttpResponseVO;
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

        try
        {
            HttpResponseVO httpResponseVO = HttpRequester.sendGET(baseurl+studentQuery);
            Tasks tasks = TasksBuilder.buildTasksObject(httpResponseVO.getBody());

            for (String taskURL : tasks.getTaskURLS())
            {
                httpResponseVO = HttpRequester.sendGET(baseurl+taskURL);
                Task task = null;
                try
                {
                    task = TaskBuilder.buildTaskObject(httpResponseVO.getBody());
                    System.out.println(task.getInstruction());
                    System.out.println(task.getParamList());
                } catch (RuntimeException e )
                {
                    e.printStackTrace();
                    httpResponseVO = HttpRequester.sendPOST(baseurl + taskURL, "Error" + e.getMessage());
                    assertEquals(200, httpResponseVO.getResponse());
                    continue;
                }


                try
                {
                    task.runInstruction();
                    httpResponseVO = HttpRequester.sendPOST(baseurl+task.getResponseURL(), task.getResult().getAnswer());
                    task.getResult().setResponse(httpResponseVO.getResponse());
                    assertTrue(task.getResult().isCorrect());
                    assertEquals(200, httpResponseVO.getResponse());
                } catch (IllegalArgumentException e)
                {
                    e.printStackTrace();
                }

            }

        } catch (Exception e)
        {
            fail();
            e.printStackTrace();
        }


    }

    @Test
    void testSendIncorrectResults() {


        try
        {
            HttpResponseVO httpResponseVO = HttpRequester.sendGET(baseurl+studentQuery);
            Tasks tasks = null;
            try {
                tasks = TasksBuilder.buildTasksObject(httpResponseVO.getBody());

            } catch (RuntimeException e) {
                e.printStackTrace();
            }

            for (String taskURL : tasks.getTaskURLS())
            {
                httpResponseVO = HttpRequester.sendGET(baseurl+taskURL);
                Task task = null;
                try
                {
                    task = TaskBuilder.buildTaskObject(httpResponseVO.getBody());
                    System.out.println(task.getInstruction());
                    System.out.println(task.getParamList());
                } catch (RuntimeException e )
                {
                    httpResponseVO = HttpRequester.sendPOST(baseurl + taskURL, "Error" + e.getMessage());
                    assertEquals(200, httpResponseVO.getResponse());
                    continue;
                }

                try
                {
                    task.runInstruction();
                    httpResponseVO = HttpRequester.sendPOST(baseurl+task.getResponseURL(), task.getResult().getAnswer()+"WRONG");
                    task.getResult().setResponse(httpResponseVO.getResponse());
                    assertFalse(task.getResult().isCorrect());
                    assertEquals(400, httpResponseVO.getResponse());
                } catch (IllegalArgumentException e)
                {
                    e.printStackTrace();
                }

            }

        } catch (Exception e)
        {
            fail();
            e.printStackTrace();
        }


    }
}
