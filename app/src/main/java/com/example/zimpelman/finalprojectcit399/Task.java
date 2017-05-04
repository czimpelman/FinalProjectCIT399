package com.example.zimpelman.finalprojectcit399;

import java.util.Calendar;

/**
 * Created by Christopher on 5/3/2017.
 */

public class Task {
    private int taskId;
    private String name;
    private Calendar cal;
    private long time;
    private int userId;

    public Task(int taskId, String name, long cal, long time, int userId){
        this.taskId = taskId;
        this.name = name;
        this.cal.setTimeInMillis(cal);
        this.time = time;
        this.userId = userId;
    }

    public Task(String name, Calendar cal, long time, int userId){
        this.name = name;
        this.cal = cal;
        this.time = time;
        this.userId = userId;
    }

    public Task(){
        name = "";
        cal = Calendar.getInstance();
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Calendar getCal() {
        return cal;
    }

    public void setCal(Long cal) {
        this.cal.setTimeInMillis(cal);
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
