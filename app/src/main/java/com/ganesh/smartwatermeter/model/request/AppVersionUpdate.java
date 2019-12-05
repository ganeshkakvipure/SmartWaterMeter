package com.ganesh.smartwatermeter.model.request;


import com.ganesh.smartwatermeter.BuildConfig;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AppVersionUpdate {

    @SerializedName("device")
    @Expose
    private String device;

    @SerializedName("version")
    @Expose
    private String version;

    public AppVersionUpdate() {
        device = "android";
        version = BuildConfig.VERSION_NAME;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
