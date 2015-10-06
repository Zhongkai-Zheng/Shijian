package com.example.alexzheng.shijian;

import java.util.AbstractList;
import java.util.ArrayList;

public class Folder {

    private ArrayList<Time> timeArray; // contains all the times in the folder
    private String name;

    public Folder(String folderName){
        this.name = folderName;
        this.timeArray = new ArrayList<Time>();
    }

    // getters and setters

    public void addTime(Time time){
        this.timeArray.add(time);
    }

    public String getName(){
        return this.name;
    }

    public ArrayList<Time> getTimeArray() {
        return timeArray;
    }

    public void setName(String x){
        this.name = x;
    }

    public void removeTime(int x){
        this.timeArray.remove(x);
    }
    public Time getTime(int x){
        return this.timeArray.get(x);
    }
}
