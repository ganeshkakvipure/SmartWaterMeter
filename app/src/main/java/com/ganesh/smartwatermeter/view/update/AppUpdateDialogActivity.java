package com.ganesh.smartwatermeter.view.update;


import android.content.Intent;
import android.net.Uri;
import android.view.View;

import com.ganesh.smartwatermeter.R;
import com.ganesh.smartwatermeter.common.base.BaseActivity;
import com.ganesh.smartwatermeter.common.base.BaseNavigator;
import com.ganesh.smartwatermeter.common.base.BaseViewModel;
import com.ganesh.smartwatermeter.common.utils.Constants;
import com.ganesh.smartwatermeter.databinding.ActivityDialogAppUpdateBinding;

public class AppUpdateDialogActivity extends BaseActivity<ActivityDialogAppUpdateBinding, BaseViewModel> {

    @Override
    public int getLayoutID() {
        return R.layout.activity_dialog_app_update;
    }

    @Override
    public void onBinding() {
        Intent intent = getIntent();
        mBinding.message.setText(intent.getStringExtra(Constants.IntentExtra.UPDATE_MSG));
        String update = intent.getStringExtra(Constants.IntentExtra.UPDATE_TYPE);
        switch (update) {
            case Constants.AppVersioning.FORCE:
                mBinding.txtNo.setVisibility(View.GONE);
                break;
        }

        mBinding.txtYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                }
            }
        });

        mBinding.txtNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    public Class getViewModel() {
        return BaseViewModel.class;
    }

    @Override
    public BaseNavigator getNavigatorReference() {
        return null;
    }

    @Override
    public String getScreenName() {
        return null;
    }

    @Override
    public void onBackPressed() {
        return;

    }
}
