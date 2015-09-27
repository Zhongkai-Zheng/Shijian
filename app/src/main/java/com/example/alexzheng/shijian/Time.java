package com.example.alexzheng.shijian;

import android.util.Log;

public class Time {

    private String name;
    private int[] startTime;
    private int[] endTime;

    private long duration;
    private int hour, minute, second;

//    public Time(String timeName, int[] startTime, int[] endTime){
//        this.name = timeName;
//        this.startTime = startTime;
//        this.endTime = endTime;
//
//        this.duration = new int[3];
//    }

    public Time(String timeName, long duration) {
        this.name = timeName;
        this.duration = duration;
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

    public long getDuration() {
        return duration;
    }

   public void printTime() {
//        int h = (int) (duration / 3600000);
//        int m = (int) (duration - h * 3600000) / 60000;
//        int s = (int) (duration - h * 3600000 - m * 60000) / 1000;
//        String hh = h < 10 ? "0" + h : h + "";
//        String mm = m < 10 ? "0" + m : m + "";
//        String ss = s < 10 ? "0" + s : s + "";
//        Log.d("time", hh + ":" + mm + ":" + ss);

       Log.d("time2", duration+"");
    }
}
