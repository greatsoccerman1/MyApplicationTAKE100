package com.example.myapplicationtake100;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import Models.groupInfo;

public class Post {

    private String userName;
    private String userId;
    private boolean isUser;
    private ArrayList<groupInfo> groupInfo;


    @SerializedName("body")
    public boolean getIsAUser() {
        return isUser;
    }
    //private String text;
    public String getUserName() {
        return userName;
    }

    public String getUserId(){
        return userId;
    }

    public ArrayList<Models.groupInfo> getGroupInfo() {
        return groupInfo;
    }
}
