package com.example.root.sequencingapp;

/**
 * Created by root on 4/11/17.
 */

public class SceneTracker {
    private static int level=0;
    private static int totalLevel=0,count=0,picassoCount=0;
    public SceneTracker( ){

    }

    public static void setLevel( int value){
        level=value;

    }

    public static int getLevel()
    {
        return level;
    }


    public static void setCount(int value){
        count = value;

    }

    public  static int getCount(){
        return count;
    }


    public static void setPicassoCount(int value){
        picassoCount=value;
    }

    public static int getPicassoCount(){
        return picassoCount;
    }


    public static void setTotalLevel(int value) {
        totalLevel = value;
    }

    public static int getTotalLevel(){
        return totalLevel;
    }



}