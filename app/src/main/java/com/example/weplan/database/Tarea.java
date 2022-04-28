package com.example.weplan.database;

public class Tarea {
    private int taskid;
    private String taskname;
    private boolean done;
    private String date;

    public Tarea(int taskid, String taskname, boolean done, String date) {
        this.taskid = taskid;
        this.taskname = taskname;
        this.done = done;
        this.date = date;
    }

    public int getTaskid() {
        return taskid;
    }

    public String getTaskname() {
        return taskname;
    }

    public boolean isDone() {
        return done;
    }

    public String getDate() {
        return date;
    }

    public void setTaskid(int taskid) {
        this.taskid = taskid;
    }

    public void setTaskname(String taskname) {
        this.taskname = taskname;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
