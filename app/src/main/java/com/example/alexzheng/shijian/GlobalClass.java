package com.example.alexzheng.shijian;

import android.provider.Settings;
import android.util.Log;

import java.util.ArrayList;

public class GlobalClass {

    public static GlobalClass globalClass = new GlobalClass();
    public static ArrayList<Folder> folderList;
    private static int onEditMode = 0;

    private static int[] tempStartTime;
    private static int[] tempEndTime;
    private static long tempDuration;

    private static int tempFolderSelection;
    private static int tempTimeSelection;
    private static boolean saveMode;

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

    public boolean getSaveMode(){
        return this.saveMode;
    }


    public void setTempStartTime(int[] x){
        this.tempStartTime = x;
    }

    public void setTempEndTime(int[] x){
        this.tempEndTime = x;
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

    public void setSaveMode(boolean x){
        this.saveMode = x;
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
