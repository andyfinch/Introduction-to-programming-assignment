package uk.ac.uos.i2p.s193805.jsonapplication.taskhandling.task;

import uk.ac.uos.i2p.s193805.jsonapplication.taskhandling.Result;
import uk.ac.uos.i2p.s193805.jsonapplication.taskhandling.exceptions.InvalidInstructionException;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract class to hold details of individual Tasks
 */
public abstract class Task {

    private String requestURL;
    private String instruction = "";
    /**
     * The Param list.
     */
    protected List<String> paramList = new ArrayList<>();
    private String responseURL;
    /**
     * The Result.
     */
    protected Result result;
    private String json;

    /**
     * Run instruction.
     */
    public void runInstruction(){
        throw new InvalidInstructionException(instruction + " is not a valid instruction");
    }

    /**
     * Gets param list.
     *
     * @return the param list
     */
    public List<String> getParamList() {
        return paramList;
    }

    /**
     * Sets param list.
     *
     * @param paramList the param list
     */
    public void setParamList(List<String> paramList) {
        this.paramList = paramList;
    }

    /**
     * Gets instruction.
     *
     * @return the instruction
     */
    public String getInstruction() {
        return instruction;
    }

    /**
     * Sets instruction.
     *
     * @param instruction the instruction
     */
    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    /**
     * Gets response url.
     *
     * @return the response url
     */
    public String getResponseURL() {
        return responseURL;
    }

    /**
     * Sets response url.
     *
     * @param responseURL the response url
     */
    public void setResponseURL(String responseURL) {
        this.responseURL = responseURL;
    }

    /**
     * Gets result.
     *
     * @return the result
     */
    public Result getResult() {
        return result;
    }

    /**
     * Sets result.
     *
     * @param result the result
     */
    public void setResult(Result result) {
        this.result = result;
    }

    /**
     * Gets request url.
     *
     * @return the request url
     */
    public String getRequestURL() {
        return requestURL;
    }

    /**
     * Sets request url.
     *
     * @param requestURL the request url
     */
    public void setRequestURL(String requestURL) {
        this.requestURL = requestURL;
    }

    /**
     * Gets json.
     *
     * @return the json
     */
    public String getJson() {
        return json;
    }

    /**
     * Sets json.
     *
     * @param json the json
     */
    public void setJson(String json) {
        this.json = json;
    }

    /**
     * Gets task id.
     *
     * @return the task id
     */
    public String getTaskID() {
        return requestURL.substring(requestURL.lastIndexOf("/") + 1);
    }

}
