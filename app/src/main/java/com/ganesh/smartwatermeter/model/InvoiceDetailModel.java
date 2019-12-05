package com.ganesh.smartwatermeter.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

public class InvoiceDetailModel extends BaseObservable {

    @SerializedName("invoice")
    @Expose
    private String invoiceNo;

    @SerializedName("units")
    @Expose
    private String readingUnit;

    @SerializedName("amount")
    @Expose
    private String amount;

    @SerializedName("period")
    @Expose
    private String invoiceDate;

    @SerializedName("link")
    @Expose
    private String invoiceLink;

    @SerializedName("p_status")
    @Expose
    private int paymentStatus;


    @Bindable
    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
        notifyPropertyChanged(com.ganesh.smartwatermeter.BR.invoiceNo);
    }

    @Bindable
    public String getReadingUnit() {
        return readingUnit;
    }

    public void setReadingUnit(String readingUnit) {
        this.readingUnit = readingUnit;
        notifyPropertyChanged(com.ganesh.smartwatermeter.BR.readingUnit);
    }

    @Bindable
    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
        notifyPropertyChanged(com.ganesh.smartwatermeter.BR.amount);
    }

    @Bindable
    public String getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(String invoiceDate) {
        this.invoiceDate = invoiceDate;
        notifyPropertyChanged(com.ganesh.smartwatermeter.BR.invoiceDate);
    }

    @Bindable
    public String getInvoiceLink() {
        return invoiceLink;
    }

    public void setInvoiceLink(String invoiceLink) {
        this.invoiceLink = invoiceLink;
        notifyPropertyChanged(com.ganesh.smartwatermeter.BR.invoiceLink);
    }


    @Bindable
    public int getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(int paymentStatus) {
        this.paymentStatus = paymentStatus;
        notifyPropertyChanged(com.ganesh.smartwatermeter.BR.paymentStatus);
    }
}
