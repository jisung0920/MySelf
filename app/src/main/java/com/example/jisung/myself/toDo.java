package com.example.jisung.myself;

/**
 * Created by jisung on 2017-05-08.
 */

public class toDo {
    private String title;
    private String createDate;
    private String deadline;
    private Boolean clear;
    private Boolean alram;

    public toDo(String title, String createDate, String deadline, Boolean clear, Boolean alram) {
        this.title = title;
        this.createDate = createDate;
        this.deadline = deadline;
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

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
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
