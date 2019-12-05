package com.ganesh.smartwatermeter.common.repository;


import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import com.ganesh.smartwatermeter.R;
import com.ganesh.smartwatermeter.common.base.App;
import com.ganesh.smartwatermeter.common.repository.db.LocalDataProvider;
import com.ganesh.smartwatermeter.common.repository.network.ApiClient;
import com.ganesh.smartwatermeter.common.repository.network.ApiInterface;
import com.ganesh.smartwatermeter.common.repository.network.RemoteDataProvider;
import com.ganesh.smartwatermeter.common.repository.network.exceptions.NoInternetException;
import com.ganesh.smartwatermeter.common.utils.Constants;
import com.ganesh.smartwatermeter.common.utils.SPManager;
import com.ganesh.smartwatermeter.common.utils.Utils;
import com.ganesh.smartwatermeter.model.ContactUsModel;
import com.ganesh.smartwatermeter.model.CustomerModel;
import com.ganesh.smartwatermeter.model.InvoiceDetailModel;
import com.ganesh.smartwatermeter.model.UsagesModel;
import com.ganesh.smartwatermeter.model.request.AppVersionUpdate;
import com.ganesh.smartwatermeter.model.request.LoginRequestModel;
import com.ganesh.smartwatermeter.model.request.SendFeedbackRequestModel;
import com.ganesh.smartwatermeter.model.request.UserNameRequestModel;
import com.ganesh.smartwatermeter.model.response.BaseResponse;
import com.ganesh.smartwatermeter.model.response.BaseResponseList;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import androidx.databinding.ObservableArrayList;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Response;


public class DataRepository implements RemoteDataProvider, LocalDataProvider {

    private static final String TAG = DataRepository.class.getSimpleName();

    private static volatile DataRepository INSTANCE;
    private ApiInterface mServices;

    private Utils utils;

    private DataRepository() {

        mServices = ApiClient.getClient();
        utils = Utils.getInstance();
    }

    public static DataRepository getInstance() {
        if (INSTANCE == null) {
            synchronized (DataRepository.class) {
                if (INSTANCE == null) {
                    INSTANCE = new DataRepository();
                }
            }
        }
        return INSTANCE;
    }


    private void handleResponse(BaseResponse baseResponse, Consumer<Throwable> error) {
        try {
            if (TextUtils.isEmpty(baseResponse.getResponseMessage())) {
                error.accept(new Throwable(App.getContext().getString(R.string.server_error)));
            } else {
                error.accept(new Throwable(baseResponse.getResponseMessage()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleResponse(BaseResponseList baseResponse, Consumer<Throwable> error) {
        try {
            if (TextUtils.isEmpty(baseResponse.getResponseMessage()))
                error.accept(new Throwable(App.getContext().getString(R.string.server_error)));
            else
                error.accept(new Throwable(baseResponse.getResponseMessage()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void noInternetAvailable(Consumer<Throwable> error) {
        try {
            error.accept(new NoInternetException(App.getContext().getString(R.string.no_internet_connection_msg)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private Disposable getDefaultDisposable() {
        return new Disposable() {
            @Override
            public void dispose() {

            }

            @Override
            public boolean isDisposed() {
                return false;
            }
        };
    }

    private boolean isNetworkAvailable() {
        return utils.isNetworkAvailable(App.getContext());
    }


    @Override
    public Disposable authenticateUser(LoginRequestModel loginRequestModel, Consumer<CustomerModel> success, Consumer<Throwable> error) {
        if (isNetworkAvailable()) {
            return mServices.authenticateUser(loginRequestModel)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(responseModel -> {
                        if ("200".equals(responseModel.getResponseCode())) {
                            success.accept(responseModel.getData());
                        } else {
                            handleResponse(responseModel, error);
                        }
                    }, error);
        } else {
            noInternetAvailable(error);
            return getDefaultDisposable();
        }
    }


    @Override
    public Disposable checkUpdate(Consumer<BaseResponse> success, Consumer<Throwable> error) {
        if (isNetworkAvailable()) {
            return mServices.checkUpdate(new AppVersionUpdate())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(responseModel -> {
                        if ("200".equals(responseModel.getResponseCode())) {
                            success.accept(responseModel);
                        } else {
                            handleResponse(responseModel, error);
                        }
                    }, error);
        } else {

            return getDefaultDisposable();
        }
    }

    @Override
    public Disposable getMeterReading(Consumer<CustomerModel> success, Consumer<Throwable> error) {
        if (isNetworkAvailable()) {
            return mServices.getMeterReading(new UserNameRequestModel(SPManager.getInstance().getMeterId()))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(responseModel -> {
                        if ("200".equals(responseModel.getResponseCode())) {
                            success.accept(responseModel.getData());
                        } else {
                            handleResponse(responseModel, error);
                        }
                    }, error);
        } else {
            noInternetAvailable(error);
            return getDefaultDisposable();
        }
    }

    @Override
    public Disposable getUsageData(Consumer<ObservableArrayList<UsagesModel>> success, Consumer<Throwable> error) {
        if (isNetworkAvailable()) {
            return mServices.getUsageData(new UserNameRequestModel(SPManager.getInstance().getMeterId()))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(responseModel -> {
                        if ("200".equals(responseModel.getResponseCode())) {
                            success.accept(responseModel.getData());
                        } else {
                            handleResponse(responseModel, error);
                        }
                    }, error);
        } else {
            noInternetAvailable(error);
            return getDefaultDisposable();
        }
    }

    @Override
    public Disposable getInvoiceList(Consumer<ObservableArrayList<InvoiceDetailModel>> success, Consumer<Throwable> error) {
        if (isNetworkAvailable()) {
            return mServices.getInvoiceList(new UserNameRequestModel(SPManager.getInstance().getMeterId()))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(responseModel -> {
                        if ("200".equals(responseModel.getResponseCode())) {
                            success.accept(responseModel.getData());
                        } else {
                            handleResponse(responseModel, error);
                        }
                    }, error);
        } else {
            noInternetAvailable(error);
            return getDefaultDisposable();
        }
    }


    @Override
    public Disposable downloadInvoice(String url, Consumer<File> success, Consumer<Throwable> error) {

        if (isNetworkAvailable()) {

            return mServices.downloadInvoice(url)
                    .flatMap((Function<Response<ResponseBody>, ObservableSource<File>>) responseBodyResponse -> {
                        Uri uri = Uri.parse(url);
                        String fileName = uri.getQueryParameter("bill_no");
                        fileName = fileName.replaceAll("/", "_");
                        return saveToDisk(responseBodyResponse.body(), fileName + ".pdf");
                    }).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(success, error);

        } else {
            noInternetAvailable(error);
            return getDefaultDisposable();
        }
    }

    @Override
    public Disposable sendFeedback(SendFeedbackRequestModel sendFeedbackRequestModel, Consumer<BaseResponse> success, Consumer<Throwable> error) {
        if (isNetworkAvailable()) {
            return mServices.sendFeedback(sendFeedbackRequestModel)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(responseModel -> {
                        if ("200".equals(responseModel.getResponseCode())) {
                            success.accept(responseModel);
                        } else {
                            handleResponse(responseModel, error);
                        }
                    }, error);
        } else {
            noInternetAvailable(error);
            return getDefaultDisposable();
        }
    }

    @Override
    public Disposable getFeedbackType(Consumer<String[]>success, Consumer<Throwable> error) {
        if (isNetworkAvailable()) {
            return mServices.getFeedbackType()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(responseModel -> {
                        if ("200".equals(responseModel.getResponseCode())) {

                            String[] strings = responseModel.getData().toArray(new String[responseModel.getData().size()]);
                            success.accept(strings);
                        } else {
                            handleResponse(responseModel, error);
                        }
                    }, error);
        } else {
            noInternetAvailable(error);
            return getDefaultDisposable();
        }
    }

    @Override
    public Disposable getContactUs(Consumer<ContactUsModel> success, Consumer<Throwable> error) {
        if (isNetworkAvailable()) {
            return mServices.getContactUs(new UserNameRequestModel(SPManager.getInstance().getMeterId()))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(responseModel -> {
                        if ("200".equals(responseModel.getResponseCode())) {
                            success.accept(responseModel.getData());
                        } else {
                            handleResponse(responseModel, error);
                        }
                    }, error);
        } else {
            noInternetAvailable(error);
            return getDefaultDisposable();
        }
    }


    private Observable<File> saveToDisk(ResponseBody body, String filename) {
        return Observable.create(new ObservableOnSubscribe<File>() {
            @Override
            public void subscribe(ObservableEmitter<File> emitter) throws Exception {


                try {
                    File folder = new File(Constants.FILE_PATH);
                    if (!folder.exists()) {
                        folder.mkdirs();
                    }

                    File destinationFile = new File(folder, filename);
                    destinationFile.setReadable(true, false);
                    InputStream inputStream = null;
                    OutputStream outputStream = null;

                    try {

                        inputStream = body.byteStream();
                        outputStream = new FileOutputStream(destinationFile);
                        byte data[] = new byte[4096];
                        int count;
                        int progress = 0;
                        long fileSize = body.contentLength();
                        Log.d(TAG, "File Size=" + fileSize);
                        while ((count = inputStream.read(data)) != -1) {
                            outputStream.write(data, 0, count);
                            progress += count;

                        }

                        outputStream.flush();

                        emitter.onNext(destinationFile);
                        return;
                    } catch (IOException e) {
                        e.printStackTrace();
                        //Pair<Integer, Long> pairs = new Pair<>(-1, Long.valueOf(-1));
                        //downloadZipFileTask.doProgress(pairs);
                        emitter.onError(e);
                        Log.d(TAG, "Failed to save the file!");
                        return;
                    } finally {
                        if (inputStream != null) inputStream.close();
                        if (outputStream != null) outputStream.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.d(TAG, "Failed to save the file!");
                    emitter.onError(e);
                    return;
                }
            }
        });


    }

}
