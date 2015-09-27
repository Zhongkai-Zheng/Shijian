package com.example.alexzheng.shijian;

import android.provider.Settings;

import java.util.ArrayList;

public class GlobalClass {

    public static GlobalClass globalClass = new GlobalClass();
    public static ArrayList<Folder> folderList;

    private static int[] tempStartTime;
    private static int[] tempEndTime;
    private static long tempDuration;

    private static int tempFolderSelection;
    private static int tempTimeSelection;

    public GlobalClass(){
        folderList = new ArrayList<Folder>();
        // 6 represents Year, Month, Day, Hour, Minute, Second

        this.tempStartTime = new int[6];
        this.tempEndTime = new int[6];

        this.tempFolderSelection = 0;
        this.tempTimeSelection = 0;
    }

    public static synchronized GlobalClass getInstance(){
        return globalClass;
    }

    public ArrayList<Folder> getFolderList(){
        return this.folderList;
    }

    public int[] getTempStartTime(){
        return this.tempStartTime;
    }

    public int[] getTempEndTime(){
        return this.tempEndTime;
    }

    public long getTempDuration(){
        return this.tempDuration;
    }

    public int getTempFolderSelection(){
        return this.tempFolderSelection;
    }

    public int getTempTimeSelection(){
        return this.tempTimeSelection;
    }


    public void setTempStartTime(int[] x){
        this.tempStartTime = x;
    }

    public void setTempEndTime(int[] x){
        this.tempEndTime = x;
    }

    public void setTempDuration(long x){
        this.tempDuration = x;
    }

    public void setTempFolderSelection(int x){
        this.tempFolderSelection = x;
    }

    public void setTempTimeSelection(int x){
        this.tempTimeSelection = x;
    }


    public void addFolder(Folder x){
        this.folderList.add(x);
    }
}
