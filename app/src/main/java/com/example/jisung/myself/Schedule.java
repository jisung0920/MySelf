package com.example.jisung.myself;

/**
 * Created by jisung on 2017-05-21.
 */

public class Schedule {
    private String time;
    private String name;
    private String locate;
    private int day;

    public Schedule(String time, String name, String locate, int day) {
        this.time = time;
        this.name = name;
        this.locate = locate;
        this.day = day;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocate() {
        return locate;
    }

    public void setLocate(String locate) {
        this.locate = locate;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }
}
