package com.example.alexzheng.shijian;

import java.util.AbstractList;
import java.util.ArrayList;

/**
 * Created by alexzheng on 9/22/15.
 */
public class Folder extends ArrayList<Time> {

    private ArrayList timeArray;
    private String name;

    public Folder(String folderName){
        this.name = folderName;
        //this.timeArray = new ArrayList();
    }

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
