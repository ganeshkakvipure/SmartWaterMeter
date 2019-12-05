package com.ganesh.smartwatermeter.model.request;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

public class SendFeedbackRequestModel extends BaseObservable {

    @SerializedName("user_name")
    @Expose
    private String customerId;


    @SerializedName("type")
    @Expose
    private String type;

    @SerializedName("message")
    @Expose
    private String comments;


    public SendFeedbackRequestModel(String customerId) {
        this.customerId = customerId;
    }

    @Bindable
    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
        notifyPropertyChanged(com.ganesh.smartwatermeter.BR.customerId);
    }


    @Bindable
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
        notifyPropertyChanged(com.ganesh.smartwatermeter.BR.type);
    }

    @Bindable
    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
        notifyPropertyChanged(com.ganesh.smartwatermeter.BR.comments);
    }


}
