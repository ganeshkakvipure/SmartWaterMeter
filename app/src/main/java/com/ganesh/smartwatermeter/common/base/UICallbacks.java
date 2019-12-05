package com.ganesh.smartwatermeter.common.base;

/**
 * Created by Ganesh on 23/05/17.
 */

public interface UICallbacks {

    int getLayoutID();

    void onBinding();

    Class getViewModel();

    BaseNavigator getNavigatorReference();

    String getScreenName();

}
