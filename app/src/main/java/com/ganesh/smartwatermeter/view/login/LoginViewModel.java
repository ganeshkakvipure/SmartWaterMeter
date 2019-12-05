package com.ganesh.smartwatermeter.view.login;


import android.text.TextUtils;

import com.ganesh.smartwatermeter.R;
import com.ganesh.smartwatermeter.common.base.App;
import com.ganesh.smartwatermeter.common.base.BaseViewModel;
import com.ganesh.smartwatermeter.common.repository.DataRepository;
import com.ganesh.smartwatermeter.common.utils.SPManager;
import com.ganesh.smartwatermeter.model.request.LoginRequestModel;

/**
 * Created by Ganesh on 3/1/2018.
 */

public class LoginViewModel extends BaseViewModel<LoginNavigator> {

    private static final String TAG = LoginViewModel.class.getSimpleName();


    public void onLoginClick(LoginRequestModel loginRequestModel) {
        if (isValid(loginRequestModel)) {
            dialogMessage.setValue(App.getContext().getString(R.string.msg_loading_dialog_login));
            dialogVisibility.setValue(true);
            login(loginRequestModel);


        }

    }


    private void login(LoginRequestModel loginRequestModel) {
        compositeDisposable.add(DataRepository.getInstance().authenticateUser(loginRequestModel, customerModel -> {
            SPManager.getInstance().setIsLogin(true);
            SPManager.getInstance().setUserName(loginRequestModel.getUserName());
            SPManager.getInstance().setMeterId(loginRequestModel.getUserName());
            SPManager.getInstance().setCustomerName(customerModel);
            dialogVisibility.setValue(false);
            mNavigator.onLoginSuccess();
        }, this::checkError));
    }


    private boolean isValid(LoginRequestModel loginRequestModel) {
        if (TextUtils.isEmpty(loginRequestModel.getUserName())) {
            mNavigator.onUsernameError();
            return false;
        } else if (TextUtils.isEmpty(loginRequestModel.getPassword())) {
            mNavigator.onPasswordError();
            return false;
        }
        return true;
    }


}
