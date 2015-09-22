package com.example.alexzheng.shijian;

/**
 * Created by alexzheng on 9/22/15.
 */
public class Time {

    private String name;
    private int[] startTime;
    private int[] endTime;

    public Time(String timeName, int[] startTime, int[] endTime){
        this.name = timeName;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public int[] getStartTime(){
        return this.startTime;
    }

    public int[] getEndTime(){
        return this.endTime;
    }

    public String getName(){
        return this.name;
    }
}
