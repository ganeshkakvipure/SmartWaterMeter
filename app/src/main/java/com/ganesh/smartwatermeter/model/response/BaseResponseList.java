package com.ganesh.smartwatermeter.model.response;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import androidx.databinding.ObservableArrayList;

public class BaseResponseList<T> {

    @SerializedName("status")
    @Expose
    private String responseCode;

    @SerializedName("message")
    @Expose
    private String responseMessage;

    @SerializedName("data")
    @Expose
    private ObservableArrayList<T> data;

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public ObservableArrayList<T> getData() {
        return data;
    }

    public void setData(ObservableArrayList<T> data) {
        this.data = data;
    }
}
