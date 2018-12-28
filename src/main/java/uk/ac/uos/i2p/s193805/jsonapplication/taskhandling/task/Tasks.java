package uk.ac.uos.i2p.s193805.jsonapplication.taskhandling.task;

import java.util.ArrayList;
import java.util.List;

public class Tasks {

    private String id;
    private List<String> taskURLS = new ArrayList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getTaskURLS() {
        return taskURLS;
    }

    public void setTaskURLS(List<String> taskURLS) {
        this.taskURLS = taskURLS;
    }

    @Override
    public String toString() {
        return "Tasks{" +
                "id='" + id + '\'' +
                ", taskURLS=" + taskURLS +
                '}';
    }
}
