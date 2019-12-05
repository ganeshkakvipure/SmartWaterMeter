package com.ganesh.smartwatermeter.model.request;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserNameRequestModel {

    @SerializedName("user_name")
    @Expose
    private String userName;

    public UserNameRequestModel(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
