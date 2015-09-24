package com.example.alexzheng.shijian;

import java.util.AbstractList;
import java.util.ArrayList;

public class Folder {

    private ArrayList<Time> timeArray;
    private String name;

    public Folder(String folderName){
        this.name = folderName;
        this.timeArray = new ArrayList<Time>();
    }

    // getters and setters

    public ArrayList<Time> getArrayList(){
        return this.timeArray;
    }

    public void addTime(Time time){
        this.timeArray.add(time);
    }

    public String getName(){
        return this.name;
    }

    public void setName(String x){
        this.name = x;
    }
}
