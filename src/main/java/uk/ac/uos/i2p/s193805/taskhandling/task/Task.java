package uk.ac.uos.i2p.s193805.taskhandling.task;

import uk.ac.uos.i2p.s193805.taskhandling.Result;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: finchaj
 * Date: 25/10/2018
 * Time: 15:35
 */

public abstract class Task {

    private String instruction;
    protected List<Object> paramList = new ArrayList<>();
    private String responseURL;
    protected Result result;

    public abstract void runInstruction();

    public List<Object> getParamList() {
        return paramList;
    }

    public void setParamList(List<Object> paramList) {
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
}