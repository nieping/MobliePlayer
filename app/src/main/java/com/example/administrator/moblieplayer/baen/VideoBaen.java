package com.example.administrator.moblieplayer.baen;

/**
 * Created by Administrator on 2018/5/16.
 */

public class VideoBaen {
    private int id ;
    private String path;
    private String name;
    private long size;

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    private int data;
    private int duration;//持续时间

    public VideoBaen(int id,String name,long size,String path,int duration,int data ){
        this.id = id;
        this.name = name;
        this.size = size;
        this.path = path;
        this.duration = duration;
        this.data = data;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }



    @Override
    public String toString() {
        return "Video [ id = " + id+ ",path=" + path + "name=" +name + "size =" + size + "]" ;
    }
}
