package com.example.jisung.myself;

/**
 * Created by jisung on 2017-05-08.
 */

public class toDo {
    private String title;
    private String createDate;
    private String time;
    private Boolean clear;
    private Boolean alram;

    public toDo(String title, String createDate, String time, Boolean clear, Boolean alram) {
        this.title = title;
        this.createDate = createDate;
        this.time = time;
        this.clear = clear;
        this.alram = alram;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Boolean getClear() {
        return clear;
    }

    public void setClear(Boolean clear) {
        this.clear = clear;
    }

    public Boolean getAlram() {
        return alram;
    }

    public void setAlram(Boolean alram) {
        this.alram = alram;
    }
}
