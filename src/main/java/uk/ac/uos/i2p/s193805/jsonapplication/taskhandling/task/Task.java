package uk.ac.uos.i2p.s193805.jsonapplication.taskhandling.task;

import uk.ac.uos.i2p.s193805.jsonapplication.taskhandling.Result;
import uk.ac.uos.i2p.s193805.jsonapplication.taskhandling.exceptions.InvalidInstructionException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: finchaj
 * Date: 25/10/2018
 * Time: 15:35
 */

public class Task {

    private String requestURL;
    private String instruction = "";
    protected List<String> paramList = new ArrayList<>();
    private String responseURL;
    protected Result result;
    private String json;
    private String taskID;

    public void runInstruction(){
        throw new InvalidInstructionException(instruction + " is not a valid instruction");
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
        return requestURL.substring(requestURL.lastIndexOf("/") + 1);
    }

    public void setTaskID(String taskID) {
        this.taskID = taskID;
    }

}
