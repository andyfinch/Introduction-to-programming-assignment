package uk.ac.uos.i2p.s193805.jsonapplication.run;

import uk.ac.uos.i2p.s193805.jsonapplication.file.FileWriter;
import uk.ac.uos.i2p.s193805.jsonapplication.file.Report;
import uk.ac.uos.i2p.s193805.jsonapplication.http.HttpRequester;
import uk.ac.uos.i2p.s193805.jsonapplication.http.HttpResponseVO;
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


/**
 * The type Application run.
 */
public class ApplicationRun {

    /**
     * The constant baseurl.
     */
    public final static String baseurl = "http://i2j.openode.io";
    /**
     * The constant studentQuery.
     */
    public final static String studentQuery = "/student?id=s193805";
    private Tasks tasks;

    /**
     * Process tasks request.
     *
     * @throws IOException any ioexception
     */
    public void processTasksRequest() throws IOException
    {
        System.out.println("Processing Tasks request from " + baseurl + studentQuery);
        HttpResponseVO httpResponseVO = new HttpRequester().sendGET(baseurl + studentQuery);
        tasks = TasksBuilder.buildTasksObject(new JSONParser(new StringReader(httpResponseVO.getBody())).parse());
        Report report = new Report();
        report.setFileName("tasks_" + tasks.getId() + ".txt");
        report.setRequestURL(baseurl + studentQuery);
        report.setRequestContent(httpResponseVO.getBody());
        FileWriter fileWriter = new FileWriter(report);
        fileWriter.writeToFile();
        System.out.println("Processing Tasks request complete");
    }

    /**
     * Process all tasks.
     *
     * @throws Exception any exception
     */
    public void processAllTasks() throws Exception
    {
        ExecutorService executor = Executors.newFixedThreadPool(tasks.getTaskURLS().size());
        List<Callable<HttpResponseVO>> callableTasks = new ArrayList<>();

        for (String taskURL : tasks.getTaskURLS())
        {

            Callable<HttpResponseVO> callableTask = () -> {
                return new HttpRequester().sendGET(baseurl + taskURL);
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
                report.setFileName("task_" + httpResponseVO.getRequestURL().substring(httpResponseVO.getRequestURL().lastIndexOf("/") + 1)+".txt");

                jsonObject = new JSONParser(new StringReader(httpResponseVO.getBody())).parse();
                task = new TaskBuilder().buildTaskObject(httpResponseVO.getBody(), jsonObject, httpResponseVO.getRequestURL());

                report.setResponseURL(baseurl + task.getResponseURL());

                task.runInstruction();
                httpResponseVO = new HttpRequester().sendPOST(baseurl + task.getResponseURL(), task.getResult().getAnswer());
                task.getResult().setResponse(httpResponseVO.getResponse());
                report.setResponseSent(task.getResult().getAnswer());

            }
            catch (JsonParseException | InvalidInstructionException e)
            {
                httpResponseVO = new HttpRequester().sendPOST(httpResponseVO.getRequestURL(), "Error: " + e.getMessage());
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

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     * @throws Exception the exception
     */
    public static void main(String[] args) throws Exception {

        ApplicationRun applicationRun = new ApplicationRun();
        applicationRun.processTasksRequest();
        applicationRun.processAllTasks();
    }
}
