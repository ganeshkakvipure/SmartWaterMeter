package com.ganesh.smartwatermeter.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BaseResponse<T> {

    @SerializedName("status")
    @Expose
    private String responseCode;

    @SerializedName("message")
    @Expose
    private String responseMessage;

    @SerializedName("update")
    @Expose
    private String update;

    @SerializedName("data")
    @Expose
    private T data;

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


    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getUpdate() {
        return update;
    }

    public void setUpdate(String update) {
        this.update = update;
    }
}
