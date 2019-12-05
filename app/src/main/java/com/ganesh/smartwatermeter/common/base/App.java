package com.ganesh.smartwatermeter.common.base;

import android.app.Application;
import android.content.Context;

import com.facebook.stetho.Stetho;

import java.lang.ref.WeakReference;


/**
 * Created by Ganesh on 11/8/2017.
 */

public class App extends Application {
    private static WeakReference<App> mInstance = new WeakReference<>(null);

    public static Context getContext() {
        return mInstance.get().getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance.clear();
        mInstance = new WeakReference<>(this);
        Stetho.initializeWithDefaults(this);
    }


}
