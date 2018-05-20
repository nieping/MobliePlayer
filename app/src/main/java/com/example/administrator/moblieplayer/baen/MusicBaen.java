package com.example.administrator.moblieplayer.baen;

/**
 * Created by Administrator on 2018/5/16.
 */

public class MusicBaen {
    private int id;
    private String path;
    private String name;
    private long size;

    public void setSize(long size) {
        this.size = size;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    private int date;
    private int duration;


    public MusicBaen(int id, String name, String path, int duration, long size, int date) {
        this.date = date;
        this.id = id;
        this.duration = duration;
        this.name = name;
        this.path = path;
        this.size = size;
        this.date = date;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }


    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }


    @Override
    public String toString() {
        return "Video [ id = " + id + ",path=" + path + "name=" + name + "duration =" + duration + "size =" + size + "]";

    }
}
