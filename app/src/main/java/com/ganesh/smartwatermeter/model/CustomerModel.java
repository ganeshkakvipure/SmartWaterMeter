package com.ganesh.smartwatermeter.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

public class CustomerModel extends BaseObservable {

    @SerializedName("reading")
    @Expose
    private String meterReading;

    @SerializedName("sync")
    @Expose
    private String lastSyncTime;

    @SerializedName("name")
    @Expose
    private String customerName;

    @SerializedName("apartment")
    @Expose
    private String customerApartment;

    @SerializedName("block")
    @Expose
    private String customerBlock;

    @SerializedName("floor")
    @Expose
    private String customerFloor;


    @SerializedName("flat")
    @Expose
    private String customerFlat;


    @SerializedName("meterId")
    @Expose
    private String meterId;

    @SerializedName("multiple")
    @Expose
    private ArrayList<String> meters;

    @SerializedName("usage")
    @Expose
    private String monthlyUsage;


    @Bindable
    public String getMeterReading() {
        return meterReading;
    }

    public void setMeterReading(String meterReading) {
        this.meterReading = meterReading;
        notifyPropertyChanged(com.ganesh.smartwatermeter.BR.meterReading);
    }

    @Bindable
    public String getLastSyncTime() {
        return lastSyncTime;
    }

    public void setLastSyncTime(String lastSyncTime) {
        this.lastSyncTime = lastSyncTime;
        notifyPropertyChanged(com.ganesh.smartwatermeter.BR.lastSyncTime);
    }

    @Bindable
    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
        notifyPropertyChanged(com.ganesh.smartwatermeter.BR.customerName);
    }

    @Bindable
    public String getCustomerApartment() {
        return customerApartment;
    }

    public void setCustomerApartment(String customerApartment) {
        this.customerApartment = customerApartment;
        notifyPropertyChanged(com.ganesh.smartwatermeter.BR.customerApartment);
    }

    @Bindable
    public String getCustomerBlock() {
        return customerBlock;
    }

    public void setCustomerBlock(String customerBlock) {
        this.customerBlock = customerBlock;
        notifyPropertyChanged(com.ganesh.smartwatermeter.BR.customerBlock);
    }

    @Bindable
    public String getCustomerFloor() {
        return customerFloor;
    }

    public void setCustomerFloor(String customerFloor) {
        this.customerFloor = customerFloor;
        notifyPropertyChanged(com.ganesh.smartwatermeter.BR.customerFloor);
    }

    @Bindable
    public String getCustomerFlat() {
        return customerFlat;
    }

    public void setCustomerFlat(String customerFlat) {
        this.customerFlat = customerFlat;
        notifyPropertyChanged(com.ganesh.smartwatermeter.BR.customerFlat);
    }

    @Bindable
    public String getMeterId() {
        return meterId;
    }

    public void setMeterId(String meterId) {
        this.meterId = meterId;
        notifyPropertyChanged(com.ganesh.smartwatermeter.BR.meterId);
    }

    @Bindable
    public ArrayList<String> getMeters() {
        return meters;
    }

    public void setMeters(ArrayList<String> meters) {
        this.meters = meters;
        notifyPropertyChanged(com.ganesh.smartwatermeter.BR.meters);
    }

    @Bindable
    public String getMonthlyUsage() {
        return monthlyUsage;
    }

    public void setMonthlyUsage(String monthlyUsage) {
        this.monthlyUsage = monthlyUsage;
        notifyPropertyChanged(com.ganesh.smartwatermeter.BR.monthlyUsage);
    }
}
