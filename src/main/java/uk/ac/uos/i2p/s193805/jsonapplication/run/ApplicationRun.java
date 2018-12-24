package uk.ac.uos.i2p.s193805.jsonapplication.run;

import uk.ac.uos.i2p.s193805.jsonapplication.file.FileWriter;
import uk.ac.uos.i2p.s193805.jsonapplication.file.Report;
import uk.ac.uos.i2p.s193805.jsonapplication.http.HttpRequester;
import uk.ac.uos.i2p.s193805.jsonapplication.http.HttpResponseVO;
import uk.ac.uos.i2p.s193805.jsonapplication.taskhandling.Result;
import uk.ac.uos.i2p.s193805.jsonapplication.taskhandling.exceptions.InvalidInstructionException;
import uk.ac.uos.i2p.s193805.jsonapplication.taskhandling.task.Task;
import uk.ac.uos.i2p.s193805.jsonapplication.taskhandling.task.Tasks;
import uk.ac.uos.i2p.s193805.jsonapplication.taskhandling.task.builder.TaskBuilder;
import uk.ac.uos.i2p.s193805.jsonapplication.taskhandling.task.builder.TasksBuilder;
import uk.ac.uos.i2p.s193805.parser.exceptions.JsonParseException;
import uk.ac.uos.i2p.s193805.parser.json.JSONParser;
import uk.ac.uos.i2p.s193805.parser.json.grammer.JsonObject;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


public class ApplicationRun {

    public final static String baseurl = "http://i2j.openode.io";
    public final static String studentQuery = "/student?id=s193805";
    private Tasks tasks;

    public void processTasksRequest() throws IOException
    {
        System.out.println("Processing Tasks request from " + baseurl + studentQuery);
        HttpResponseVO httpResponseVO = HttpRequester.sendGET(baseurl + studentQuery);
        tasks = TasksBuilder.buildTasksObject(new JSONParser(new StringReader(httpResponseVO.getBody())).parse());
        Report report = new Report();
        report.setFileName("tasks_" + tasks.getId() + ".txt");
        report.setRequestURL(baseurl + studentQuery);
        report.setRequestContent(httpResponseVO.getBody());
        FileWriter fileWriter = new FileWriter(report);
        fileWriter.writeToFile();
        System.out.println("Processing Tasks request complete");
    }

    public void processAllTasks() throws Exception
    {
        ExecutorService executor = Executors.newFixedThreadPool(tasks.getTaskURLS().size());
        List<Callable<HttpResponseVO>> callableTasks = new ArrayList<>();

        for (String taskURL : tasks.getTaskURLS())
        {

            Callable<HttpResponseVO> callableTask = () -> {
                return HttpRequester.sendGET(baseurl + taskURL);
            };
            callableTasks.add(callableTask);


        }

        List<Future<HttpResponseVO>> futures = executor.invokeAll(callableTasks);

        for (Future<HttpResponseVO> future : futures)
        {

            Report report = new Report();
            HttpResponseVO httpResponseVO = future.get();
            System.out.println("Processing Task from " + httpResponseVO.getRequestURL());

            Task task = null;
            JsonObject jsonObject;
            try
            {
                report.setRequestContent(httpResponseVO.getBody());
                report.setRequestURL(httpResponseVO.getRequestURL());
                report.setFileName(httpResponseVO.getRequestURL().substring(httpResponseVO.getRequestURL().lastIndexOf("/") + 1)+".txt");

                jsonObject = new JSONParser(new StringReader(httpResponseVO.getBody())).parse();
                task = new TaskBuilder().buildTaskObject(httpResponseVO.getBody(), jsonObject, httpResponseVO.getRequestURL());

                report.setResponseURL(baseurl + task.getResponseURL());

                task.runInstruction();
                httpResponseVO = HttpRequester.sendPOST(baseurl + task.getResponseURL(), task.getResult().getAnswer());
                task.getResult().setResponse(httpResponseVO.getResponse());
                report.setResponseSent(task.getResult().getAnswer());

            }
            catch (InvalidInstructionException e)
            {
                httpResponseVO = HttpRequester.sendPOST(httpResponseVO.getRequestURL(), "Error: " + e.getMessage());
                report.setResponseSent("Error: " + e.getMessage());


            }catch (JsonParseException e)
            {
                task = new Task();
                task.setJson(httpResponseVO.getBody());
                task.setRequestURL(httpResponseVO.getRequestURL());
                httpResponseVO = HttpRequester.sendPOST(httpResponseVO.getRequestURL(), "Error: " + e.getMessage());
                report.setResponseSent("Error: " + e.getMessage());


            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            finally
            {
                report.setResponse(httpResponseVO.getResponse());
                FileWriter fileWriter = new FileWriter(report);
                fileWriter.writeToFile();
                executor.shutdown();
                System.out.println("Processing Task complete " + httpResponseVO.getRequestURL());
            }




        }
    }

    public static void main(String[] args) throws Exception {

        ApplicationRun applicationRun = new ApplicationRun();
        applicationRun.processTasksRequest();
        applicationRun.processAllTasks();
    }
}
