package com.ganesh.smartwatermeter.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

public class ContactUsModel extends BaseObservable {


    @SerializedName("mobile")
    @Expose
    private String mobileNo;

    @SerializedName("email")
    @Expose
    private String emailId;

    @SerializedName("website")
    @Expose
    private String website;

    @SerializedName("apartment")
    @Expose
    private String apartmentName;

    @SerializedName("address")
    @Expose
    private String address;

    @SerializedName("logo")
    @Expose
    private String imageUrl;

    @Bindable
    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
        notifyPropertyChanged(com.ganesh.smartwatermeter.BR.mobileNo);

    }

    @Bindable
    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
        notifyPropertyChanged(com.ganesh.smartwatermeter.BR.emailId);
    }


    @Bindable
    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
        notifyPropertyChanged(com.ganesh.smartwatermeter.BR.website);
    }



    @Bindable
    public String getApartmentName() {
        return apartmentName;
    }

    public void setApartmentName(String apartmentName) {
        this.apartmentName = apartmentName;
        notifyPropertyChanged(com.ganesh.smartwatermeter.BR.apartmentName);
    }

    @Bindable
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
        notifyPropertyChanged(com.ganesh.smartwatermeter.BR.address);
    }

    @Bindable
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        notifyPropertyChanged(com.ganesh.smartwatermeter.BR.imageUrl);
    }
}
