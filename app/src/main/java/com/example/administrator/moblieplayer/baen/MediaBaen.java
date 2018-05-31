package com.example.administrator.moblieplayer.baen;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/5/30.
 */

public class MediaBaen implements Serializable {
    private String name;
    private String path;
    private String size;
    private  int data;
    private String artist;
    public MediaBaen(String name,String path,String size,int data,String artist){
        this.artist = artist;
        this.path = path;
        this.name = name;
        this.data = data;
        this.size = size;
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

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    @Override
    public String toString() {
        return "MediaBaen{" +
                "name='" + name + '\'' +
                ", path='" + path + '\'' +
                ", size=" + size +
                ", data=" + data +
                ", artist='" + artist + '\'' +
                '}';
    }
}
