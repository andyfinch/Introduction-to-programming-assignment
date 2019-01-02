package uk.ac.uos.i2p.s193805.jsonapplication.taskhandling.task;

import java.util.ArrayList;
import java.util.List;

/**
 * Value object to hold details for initial Tasks data
 */
public class Tasks {

    private String id;
    private List<String> taskURLS = new ArrayList<>();

    /**
     * Gets id.
     *
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets task urls.
     *
     * @return the task urls
     */
    public List<String> getTaskURLS() {
        return taskURLS;
    }

    /**
     * Sets task urls.
     *
     * @param taskURLS the task urls
     */
    public void setTaskURLS(List<String> taskURLS) {
        this.taskURLS = taskURLS;
    }


}
