package com.ganesh.smartwatermeter.view.splash;


import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.WindowManager;

import com.ganesh.smartwatermeter.R;
import com.ganesh.smartwatermeter.common.AppVersionCheckService;
import com.ganesh.smartwatermeter.common.base.BaseActivity;
import com.ganesh.smartwatermeter.common.base.BaseNavigator;
import com.ganesh.smartwatermeter.common.utils.SPManager;
import com.ganesh.smartwatermeter.databinding.ActivitySplashScreenBinding;
import com.ganesh.smartwatermeter.view.home.HomeActivity;
import com.ganesh.smartwatermeter.view.login.LoginActivity;

public class SplashScreenActivity extends BaseActivity<ActivitySplashScreenBinding, SplashViewModel> implements SplashNavigator {
    private static final String TAG = SplashScreenActivity.class.getSimpleName();
    // Splash screen timer
    private static int SPLASH_TIME_OUT = 2000;
    private Context mContext;

    @Override
    public int getLayoutID() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        return R.layout.activity_splash_screen;
    }

    @Override
    public void onBinding() {
        mContext = SplashScreenActivity.this;


        /*
         * Showing splash screen with a timer. This will be useful when you
         * want to show case your app logo / company
         */
        new Handler().postDelayed(() -> {


            Intent intent = new Intent(mContext, AppVersionCheckService.class);
            AppVersionCheckService.enqueueWork(this, intent);

            SPManager spManager = SPManager.getInstance();
            if (spManager.isLogin()) { // if user logged in

                startActivity(new Intent(mContext, HomeActivity.class));

            } else {
                startActivity(new Intent(mContext, LoginActivity.class));
            }

            SplashScreenActivity.this.finish();
        }, SPLASH_TIME_OUT);
    }

    @Override
    public Class getViewModel() {
        return SplashViewModel.class;
    }

    @Override
    public BaseNavigator getNavigatorReference() {
        return SplashScreenActivity.this;
    }

    @Override
    public String getScreenName() {
        return TAG;
    }


    @Override
    public void onError(String errorMessage) {

    }

    @Override
    public void onNoInternetConnection() {

    }
}
