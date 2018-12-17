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

public abstract class Task implements FileWritable {

    private String requestURL;
    private String instruction;
    protected List<String> paramList = new ArrayList<>();
    private String responseURL;
    protected Result result;
    private String json;

    public abstract void runInstruction();

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

    @Override
    public String getFileName() {
        return "src/Results/" + "task_" + requestURL.substring(requestURL.lastIndexOf("/")+1) + ".txt";
    }

    @Override
    public String getContent() {
        StringBuilder content = new StringBuilder();
        content.append("Json returned from the server:").append("\n");
        content.append(json).append("\n\n");
        content.append("Params were:" );
        for (String param : paramList) {
            content.append(param).append(" ");
        }
        content.append(" and these were to be ").append(instruction).append("ed together").append("\n\n");
        content.append("The result was ").append(result.getAnswer());


        return content.toString();
    }
}
