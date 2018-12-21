package uk.ac.uos.i2p.s193805.taskhandling.task;

import uk.ac.uos.i2p.s193805.file.FileWritable;
import uk.ac.uos.i2p.s193805.taskhandling.Result;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: finchaj
 * Date: 25/10/2018
 * Time: 15:35
 */

public class Task implements FileWritable {

    private String requestURL;
    private String instruction = "";
    protected List<String> paramList = new ArrayList<>();
    private String responseURL;
    protected Result result;
    private String json;
    private String taskID;

    public void runInstruction(){
        throw new UnsupportedOperationException(instruction + " is not a valid instruction");
    }

    public List<String> getParamList() {
        return paramList;
    }

    public void setParamList(List<String> paramList) {
        this.paramList = paramList;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public String getResponseURL() {
        return responseURL;
    }

    public void setResponseURL(String responseURL) {
        this.responseURL = responseURL;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public String getRequestURL() {
        return requestURL;
    }

    public void setRequestURL(String requestURL) {
        this.requestURL = requestURL;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public String getTaskID() {
        return taskID;
    }

    public void setTaskID(String taskID) {
        this.taskID = taskID;
    }

    @Override
    public String getFileName() {
        return "src/Results/" + "task_" + requestURL.substring(requestURL.lastIndexOf("/")+1) + ".txt";
    }

    @Override
    public String getContent() {
        StringBuilder content = new StringBuilder();
        content.append("Json returned from the server:").append("\n");
        content.append(json).append("\n\n");
        content.append("Params:" );
        for (String param : paramList) {
            content.append(param).append(" ");
        }
        content.append("\n\n");

        content.append("Instruction: ").append(instruction.toUpperCase()).append("\n\n");

        if ( result != null)
        {
            content.append("Result: ").append(result.getAnswer());
        }
        else
        {
            content.append("The result was not calculated");
        }



        return content.toString();
    }
}
