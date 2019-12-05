package com.ganesh.smartwatermeter.common.base;

/**
 * Created by Ganesh on 3/1/2018.
 */

public interface BaseNavigator {

    void onError(String errorMessage);

    void onNoInternetConnection();
}
