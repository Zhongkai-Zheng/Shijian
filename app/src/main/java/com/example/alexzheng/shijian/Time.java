package com.example.alexzheng.shijian;

public class Time {

    private String name;
    private int[] startTime;
    private long duration;
    private String durationString;

    public Time(String timeName, long duration) {
        this.name = timeName;
        this.duration = duration;
        formatDuration();
    }

    public int[] getStartTime(){
        return this.startTime;
    }

    public String getName(){
        return this.name;
    }

    public long getDuration() {
        return duration;
    }

    public String getDurationString() {
        return durationString;
    }

    public void setStartTime(int[] x){
        this.startTime = x;
    }

   public void formatDuration() {
        int h = (int) (duration / 3600000);
        int m = (int) (duration - h * 3600000) / 60000;
        int s = (int) (duration - h * 3600000 - m * 60000) / 1000;
        String hh = h < 10 ? "0" + h : h + "";
        String mm = m < 10 ? "0" + m : m + "";
        String ss = s < 10 ? "0" + s : s + "";
        durationString = hh + ":" + mm + ":" + ss;
    }
}
