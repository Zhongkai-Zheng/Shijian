package com.example.alexzheng.shijian;

import android.provider.Settings;

import java.util.ArrayList;

/**
 * Created by alexzheng on 9/22/15.
 */
public class GlobalClass extends ArrayList<Folder>{

    private static GlobalClass globalClass = new GlobalClass();
    private ArrayList folderList;


    public GlobalClass(){

    }

    public static synchronized GlobalClass getInstance(){
        return globalClass;
    }

}
