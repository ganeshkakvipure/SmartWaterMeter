package com.ganesh.smartwatermeter.common.base;


import com.ganesh.smartwatermeter.R;
import com.ganesh.smartwatermeter.common.repository.network.exceptions.NoInternetException;

import java.net.SocketException;
import java.util.concurrent.TimeoutException;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import io.reactivex.disposables.CompositeDisposable;


/**
 * Created by Ganesh on 3/1/2018.
 */

public class BaseViewModel<N extends BaseNavigator> extends ViewModel {
    protected CompositeDisposable compositeDisposable = new CompositeDisposable();
    protected N mNavigator;
    protected MutableLiveData<Boolean> dialogVisibility = new MutableLiveData<>();
    protected MutableLiveData<String> dialogMessage = new MutableLiveData<>();


    public void setNavigator(N mNavigator) {
        this.mNavigator = mNavigator;
    }


    protected void checkError(Throwable throwable) {
        String throwableMessage = throwable.getMessage();
        dialogVisibility.setValue(false);
        if (throwable instanceof NoInternetException) {
            mNavigator.onNoInternetConnection();
        } else if (throwable instanceof SocketException || throwable instanceof TimeoutException) {
            mNavigator.onError(App.getContext().getString(R.string.poor_internet_connection) + "\n" + throwableMessage);
        } else if (throwable instanceof Exception) {
            if (throwableMessage.length() > 150) {
                throwableMessage = throwableMessage.substring(0, 150);
            }
            mNavigator.onError(App.getContext().getString(R.string.server_error) + "\n" + throwableMessage);
        } else {
            if (throwableMessage.contains("500") || throwableMessage.contains("Socket") || throwableMessage.contains("connect")
                    || throwableMessage.contains("timed out") || throwableMessage.contains("time out") || throwableMessage.contains("connection")) {
                mNavigator.onError(App.getContext().getString(R.string.no_internet_connection) + "\n" + throwableMessage);
            } else if (throwableMessage.contains("Software caused connection abort")) {
                mNavigator.onError(App.getContext().getString(R.string.poor_internet_connection) + "\n" + throwableMessage);
            } else {
                mNavigator.onError(throwableMessage);
            }
        }
    }


    @Override
    protected void onCleared() {
        super.onCleared();
        mNavigator = null;
        if (!compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
    }


}
