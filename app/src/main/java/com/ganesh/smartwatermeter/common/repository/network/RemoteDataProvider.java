package com.ganesh.smartwatermeter.common.repository.network;


import com.ganesh.smartwatermeter.model.ContactUsModel;
import com.ganesh.smartwatermeter.model.CustomerModel;
import com.ganesh.smartwatermeter.model.InvoiceDetailModel;
import com.ganesh.smartwatermeter.model.UsagesModel;
import com.ganesh.smartwatermeter.model.request.LoginRequestModel;
import com.ganesh.smartwatermeter.model.request.SendFeedbackRequestModel;
import com.ganesh.smartwatermeter.model.response.BaseResponse;

import java.io.File;

import androidx.databinding.ObservableArrayList;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by Ganesh on 23/05/17.
 */
public interface RemoteDataProvider {


    Disposable authenticateUser(LoginRequestModel loginRequestModel,
                                Consumer<CustomerModel> success,
                                Consumer<Throwable> error);


    Disposable checkUpdate(Consumer<BaseResponse> success, Consumer<Throwable> error);


    Disposable getMeterReading(Consumer<CustomerModel> success,
                               Consumer<Throwable> error);

    Disposable getUsageData(Consumer<ObservableArrayList<UsagesModel>> success,
                            Consumer<Throwable> error);

    Disposable getInvoiceList(Consumer<ObservableArrayList<InvoiceDetailModel>> success,
                              Consumer<Throwable> error);

    Disposable downloadInvoice(String url, Consumer<File> success, Consumer<Throwable> error);


    Disposable sendFeedback(SendFeedbackRequestModel sendFeedbackRequestModel, Consumer<BaseResponse> success,
                            Consumer<Throwable> error);

    Disposable getFeedbackType(Consumer<String[]> success,
                               Consumer<Throwable> error);

    Disposable getContactUs(Consumer<ContactUsModel> success,
                            Consumer<Throwable> error);
}
