package com.ganesh.smartwatermeter.model.request;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

public class LoginRequestModel extends BaseObservable {

    @SerializedName("user_name")
    @Expose
    private String userName;

    @SerializedName("user_password")
    @Expose
    private String password;

    @SerializedName("fcm")
    @Expose
    private String fcmToken;


    @Bindable
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
        notifyPropertyChanged(com.ganesh.smartwatermeter.BR.userName);

    }

    @Bindable
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        notifyPropertyChanged(com.ganesh.smartwatermeter.BR.password);
    }

    public String getFcmToken() {
        return fcmToken;
    }

    public void setFcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }
}
