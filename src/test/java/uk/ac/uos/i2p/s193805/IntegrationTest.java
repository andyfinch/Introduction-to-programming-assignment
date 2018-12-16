package uk.ac.uos.i2p.s193805;

import org.junit.jupiter.api.Test;
import uk.ac.uos.i2p.s193805.http.HttpRequester;
import uk.ac.uos.i2p.s193805.http.HttpResponseVO;
import uk.ac.uos.i2p.s193805.parser.json.JSONParser;
import uk.ac.uos.i2p.s193805.taskhandling.task.Task;
import uk.ac.uos.i2p.s193805.taskhandling.task.Tasks;
import uk.ac.uos.i2p.s193805.taskhandling.task.builder.TaskBuilder;
import uk.ac.uos.i2p.s193805.taskhandling.task.builder.TasksBuilder;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

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
            HttpResponseVO httpResponseVO1 = HttpRequester.sendGET(baseurl+studentQuery);
            Tasks tasks = TasksBuilder.buildTasksObject(new JSONParser(new StringReader(httpResponseVO1.getBody())).parse());

            ExecutorService executor = Executors.newFixedThreadPool(tasks.getTaskURLS().size()*10);
            List<Callable<HttpResponseVO>> callableTasks = new ArrayList<>();


            long start = System.currentTimeMillis();
            for (int i = 0; i < 10; i++) {
                for (String taskURL : tasks.getTaskURLS())
                {

                    Callable<HttpResponseVO> callableTask = () -> {
                        return HttpRequester.sendGET(baseurl+taskURL);
                    };
                    callableTasks.add(callableTask);


                }
            }


            List<Future<HttpResponseVO>> futures = executor.invokeAll(callableTasks);
            long end = System.currentTimeMillis();
            System.out.println(end-start);

            for (Future<HttpResponseVO> future : futures) {

                HttpResponseVO httpResponseVO = future.get();

                Task task = null;
                try
                {
                    task = TaskBuilder.buildTaskObject(new JSONParser(new StringReader(httpResponseVO.getBody())).parse());
                    System.out.println(task.getInstruction());
                    System.out.println(task.getParamList());
                } catch (RuntimeException e )
                {
                    e.printStackTrace();
                    //httpResponseVO = HttpRequester.sendPOST(baseurl + task.getRequestURL(), "Error" + e.getMessage());
                    //assertEquals(200, httpResponseVO.getResponse());
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


            /*for (String taskURL : tasks.getTaskURLS())
            {

                Callable<HttpResponseVO> callableTask = () -> {
                    //TimeUnit.MILLISECONDS.sleep(300);
                    return HttpRequester.sendGET(baseurl+taskURL);
                };
                callableTasks.add(callableTask);
                Future<HttpResponseVO> future =
                        executor.submit(callableTask);

                httpResponseVO = future.get();

                //httpResponseVO = HttpRequester.sendGET(baseurl+taskURL);
                Task task = null;
                try
                {
                    task = TaskBuilder.buildTaskObject(new JSONParser(new StringReader(httpResponseVO.getBody())).parse());
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
                long end = System.currentTimeMillis();
                System.out.println(end-start);

            }*/



        } catch (Exception e)
        {
            //fail();
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
                tasks = TasksBuilder.buildTasksObject(new JSONParser(new StringReader(httpResponseVO.getBody())).parse());

            } catch (RuntimeException e) {
                e.printStackTrace();
            }

            long start = System.currentTimeMillis();
            for (String taskURL : tasks.getTaskURLS())
            {
                httpResponseVO = HttpRequester.sendGET(baseurl+taskURL);
                Task task = null;
                try
                {
                    task = TaskBuilder.buildTaskObject(new JSONParser(new StringReader(httpResponseVO.getBody())).parse());
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
            long end = System.currentTimeMillis();
            System.out.println(end-start);

        } catch (Exception e)
        {
            fail();
            e.printStackTrace();
        }


    }
}
