package com.example.alexzheng.shijian;

import android.provider.Settings;
import android.util.Log;

import java.util.ArrayList;

public class GlobalClass {

    public static GlobalClass globalClass = new GlobalClass();
    public static ArrayList<Folder> folderList;

    private static int onEditMode = 0; // COMMENT WHAT THIS IS PLEASE, AND WHAT EACH EDIT MODE MEANS

    private static int[] tempStartTime; // time stamp for when user began recording time
    private static long tempDuration; // time elapsed on chronometer

    private static int tempFolderSelection; // index of folder
    private static int tempTimeSelection; // index of time

    public GlobalClass(){
        folderList = new ArrayList<Folder>();

        // 6 represents Year, Month, Day, Hour, Minute, Second
        this.tempStartTime = new int[6];

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

    public long getTempDuration(){
        return this.tempDuration;
    }

    public int getTempFolderSelection(){
        return this.tempFolderSelection;
    }

    public int getTempTimeSelection(){
        return this.tempTimeSelection;
    }

    public String[] getFolderNames(){
        String[] folderNames = new String[getFolderList().size()];
        for(int i = 0; i < getFolderList().size(); i++){
            folderNames[i] = getFolderList().get(i).getName();
        }

        return folderNames;
    }

    public Folder getFolder(int x){
        return this.getFolderList().get(x);
    }


    public void setTempStartTime(int[] x){
        this.tempStartTime = x;
    }

    public void setTempDuration(long x){
        this.tempDuration = (x - 1);
    }

    public void setTempFolderSelection(int x){
        this.tempFolderSelection = x;
    }

    public void setTempTimeSelection(int x){
        this.tempTimeSelection = x;
    }


    public void addFolder(Folder x){
        this.folderList.add(0, x);
    }

    public void removeFolder(int x){
        folderList.remove(x);
    }

    public void setOnEditMode(int x){
        this.onEditMode = x;
    }

    public int getOnEditMode(){
        return this.onEditMode;
    }
}
