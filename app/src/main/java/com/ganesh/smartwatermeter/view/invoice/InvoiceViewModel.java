package com.ganesh.smartwatermeter.view.invoice;

import android.Manifest;

import com.ganesh.smartwatermeter.common.base.BaseViewModel;
import com.ganesh.smartwatermeter.common.repository.DataRepository;
import com.ganesh.smartwatermeter.model.InvoiceDetailModel;

import java.io.File;

import androidx.databinding.ObservableArrayList;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.functions.Consumer;

public class InvoiceViewModel extends BaseViewModel<InvoiceNavigator> {

    public String[] permissions = {Manifest.permission.READ_PHONE_STATE,Manifest.permission.WRITE_EXTERNAL_STORAGE};;
    private InvoiceDetailModel selectedInvoice;
    private File currentDownloadedFile;

    private MutableLiveData<ObservableArrayList<InvoiceDetailModel>> invoiceModels = new MutableLiveData<>();

    public MutableLiveData<ObservableArrayList<InvoiceDetailModel>> getInvoiceModels() {
        return invoiceModels;
    }

    public File getCurrentDownloadedFile() {
        return currentDownloadedFile;
    }

    public void onInvoiceClick(InvoiceDetailModel model) {
        selectedInvoice = model;
        mNavigator.askPermission(permissions);

    }

    public void getInvoiceList() {
        dialogMessage.setValue("Loading...");
        dialogVisibility.setValue(true);
        compositeDisposable.add(DataRepository.getInstance().getInvoiceList(new Consumer<ObservableArrayList<InvoiceDetailModel>>() {
            @Override
            public void accept(ObservableArrayList<InvoiceDetailModel> invoiceDetailModels) throws Exception {
                dialogVisibility.setValue(false);
                invoiceModels.setValue(invoiceDetailModels);

            }
        }, this::checkError));
    }

    public void downloadInvoice() {
        dialogMessage.setValue("Invoice Downloading...");
        dialogVisibility.setValue(true);
        compositeDisposable.add(DataRepository.getInstance().downloadInvoice(selectedInvoice.getInvoiceLink(), new Consumer<File>() {
            @Override
            public void accept(File file) throws Exception {
                currentDownloadedFile=file;
                dialogVisibility.setValue(false);
                mNavigator.oniInvoiceDownloaded(file);
            }
        }, this::checkError));
    }
}
