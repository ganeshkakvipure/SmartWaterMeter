package com.ganesh.smartwatermeter.common.utils;


import android.content.Context;
import android.content.SharedPreferences;

import com.ganesh.smartwatermeter.common.base.App;
import com.ganesh.smartwatermeter.model.CustomerModel;
import com.google.gson.Gson;

/**
 * Created by Ganesh on 23/11/17.
 */
public class SPManager {
    private static SPManager myManager;
    private static SharedPreferences s;

    private String IS_LOGIN = "isLogin";
    private String USER_NAME = "userName";
    private String CUSTOMER_DATA = "customer_data";
    private String METER_ID = "meterID";


    private SPManager(Context context) {
        if (s == null)
            s = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
    }

    public static SPManager getInstance() {
        return myManager != null ? myManager : new SPManager(App.getContext());
    }

    private void saveString(String key, String data) {
        SharedPreferences.Editor editor = s.edit();
        editor.putString(key, data);
        editor.commit();
    }

    private void saveInt(String key, int data) {
        SharedPreferences.Editor editor = s.edit();
        editor.putInt(key, data);
        editor.commit();
    }

    private void saveBoolean(String key, boolean data) {
        SharedPreferences.Editor editor = s.edit();
        editor.putBoolean(key, data);
        editor.commit();
    }

    private void delete(String key) {
        SharedPreferences.Editor editor = s.edit();
        //editor.putString(key, "null");
        editor.remove(key);
        //editor.clear();
        editor.commit();
    }

    private String retrieveString(String key) {
        return s.getString(key, "");
    }

    private int retrieveInt(String key) {
        return s.getInt(key, 0);
    }

    private boolean retrieveBool(String key) {
        return s.getBoolean(key, false);
    }

    private boolean retrieveBool(String key, boolean defaultValue) {
        return s.getBoolean(key, defaultValue);
    }


    private synchronized final void saveObject(String key, Object model) {
        s.edit().putString(key, new Gson().toJson(model)).commit();
    }

    private synchronized final Object retrieveObject(String key, Class<?> modelClass) {
        return new Gson().fromJson(s.getString(key, null), modelClass);
    }


    public boolean isLogin() {
        return retrieveBool(IS_LOGIN);
    }

    public void setIsLogin(boolean isLogin) {
        saveBoolean(IS_LOGIN, isLogin);
    }


    public void logout() {
        SharedPreferences.Editor editor = s.edit();
        editor.clear();
        editor.commit();
    }


    public String getUserName() {
        return retrieveString(USER_NAME);
    }

    public void setUserName(String userName) {
        saveString(USER_NAME, userName);
    }


    public CustomerModel getCustomerData() { return (CustomerModel) retrieveObject(CUSTOMER_DATA, CustomerModel.class);
    }

    public void setCustomerName(CustomerModel customerModel) {
        saveObject(CUSTOMER_DATA, customerModel);
    }




    public String getMeterId() {
        return retrieveString(METER_ID);
    }

    public void setMeterId(String meterId) {
        saveString(METER_ID, meterId);
    }


}