package com.ganesh.smartwatermeter.view.login;


import android.content.Intent;
import android.view.WindowManager;

import com.ganesh.smartwatermeter.R;
import com.ganesh.smartwatermeter.common.base.BaseActivity;
import com.ganesh.smartwatermeter.common.base.BaseNavigator;
import com.ganesh.smartwatermeter.common.utils.Utils;
import com.ganesh.smartwatermeter.databinding.ActivityLoginBinding;
import com.ganesh.smartwatermeter.model.request.LoginRequestModel;
import com.ganesh.smartwatermeter.view.home.HomeActivity;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends BaseActivity<ActivityLoginBinding, LoginViewModel> implements LoginNavigator {

    private static final String TAG = LoginActivity.class.getSimpleName();

    @Override
    public int getLayoutID() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        return R.layout.activity_login;
    }

    @Override
    public void onBinding() {
        mBinding.setModel(new LoginRequestModel());
        mBinding.setViewModel(mViewModel);
    }

    @Override
    public Class getViewModel() {
        return LoginViewModel.class;
    }

    @Override
    public BaseNavigator getNavigatorReference() {
        return this;
    }

    @Override
    public String getScreenName() {
        return TAG;
    }

    @Override
    public void onUsernameError() {
        Utils.getInstance().showErrorMsg(mContext, getString(R.string.error_empty_username));
    }

    @Override
    public void onPasswordError() {
        Utils.getInstance().showErrorMsg(mContext, getString(R.string.error_empty_password));
    }

    @Override
    public void onLoginSuccess() {
        Intent intent;
        intent = new Intent(mContext, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onError(String errorMessage) {
        Utils.getInstance().showErrorMsg(mContext, errorMessage);
    }

    @Override
    public void onNoInternetConnection() {
        Utils.getInstance().showNoInternetMsg(mContext);
    }
}

