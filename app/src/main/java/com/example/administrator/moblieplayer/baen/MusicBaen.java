package com.example.administrator.moblieplayer.baen;

/**
 * Created by Administrator on 2018/5/16.
 */

public class MusicBaen {
    private int id;
    private String name;

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    private int date;
    public MusicBaen(int id,String name,String path,String resolution,long size,int date) {
        this.date = date;
        this.id = id;
        this.name =name;
        this.path = path;
        this.resolution = resolution;
        this.size  =size;
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

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    private String path;
    private String resolution;
    private Long size;

    @Override
    public String toString() {
        return "Video [ id = " + id+ ",path=" + path + "name=" +name +"resolution =" +resolution + "size =" + size + "]" ;

    }
}
