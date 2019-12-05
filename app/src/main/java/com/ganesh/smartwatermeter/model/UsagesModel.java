package com.ganesh.smartwatermeter.model;

import com.ganesh.smartwatermeter.BR;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

public class UsagesModel extends BaseObservable {

    @SerializedName("date")
    @Expose
    private String date;

    @SerializedName("value")
    @Expose
    private String value;

    @Bindable
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
        notifyPropertyChanged(com.ganesh.smartwatermeter.BR.date);
    }


    @Bindable
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
        notifyPropertyChanged(com.ganesh.smartwatermeter.BR.value);
    }
}
