package com.example.alexzheng.shijian;

import android.provider.Settings;

import java.util.ArrayList;

/**
 * Created by alexzheng on 9/22/15.
 */
public class GlobalClass extends ArrayList<Folder>{

    private static GlobalClass globalClass = new GlobalClass();
    private ArrayList folderList;
    private int[] tempStartTime;
    private int[] tempEndTime;
    private int[] tempDuration;
    private int tempFolderSelection;
    private int tempTimeSelection;

    public GlobalClass(){
        this.folderList = new ArrayList();
        this.tempStartTime = new int[]{0, 0, 0, 0, 0, 0};
        this.tempEndTime = new int[]{0, 0, 0, 0, 0, 0};
        this.tempDuration = new int[]{0, 0, 0, 0, 0, 0};
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

    public int[] getTempDuration(){
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

    public void setTempDuration(int[] x){
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
