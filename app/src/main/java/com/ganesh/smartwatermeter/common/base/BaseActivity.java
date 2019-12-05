package com.ganesh.smartwatermeter.common.base;


import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.ganesh.smartwatermeter.R;
import com.ganesh.smartwatermeter.common.utils.Constants;
import com.ganesh.smartwatermeter.common.utils.SPManager;
import com.ganesh.smartwatermeter.common.widget.CustomDialog;
import com.ganesh.smartwatermeter.common.widget.LoadingDialog;
import com.ganesh.smartwatermeter.databinding.ToolbarBinding;
import com.ganesh.smartwatermeter.view.login.LoginActivity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProviders;


/**
 * Created by Ganesh on 4/7/17.
 */

public abstract class BaseActivity<T extends ViewDataBinding, V extends BaseViewModel> extends AppCompatActivity implements UICallbacks {

    protected T mBinding;
    protected V mViewModel;
    protected Context mContext;
    private LoadingDialog loadingDialog;
    private CustomDialog customDialog;
    private ToolbarBinding toolbarBinding;
    private MutableLiveData<Integer> customDialogButtonClick = new MutableLiveData<>();


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        mContext = this;
        super.onCreate(savedInstanceState);
        overridePendingTransitionEnter();
        mBinding = DataBindingUtil.setContentView(BaseActivity.this, getLayoutID());
        mViewModel = (V) ViewModelProviders.of(BaseActivity.this).get(getViewModel());
        mViewModel.setNavigator(getNavigatorReference());
        createDialog();
        createAlertDialog();
        onBinding();
    }

    public MutableLiveData<Integer> getCustomDialogButtonClick() {
        return customDialogButtonClick;
    }

    /**
     * Init the custom alert dialog instance
     */
    private void createAlertDialog() {
        customDialog = new CustomDialog(mContext);
    }

    /**
     * show custom alert dialog with listener
     *
     * @param dialogTitle
     * @param message
     * @param requestCode
     */
    protected void showConfirmationDialog(String dialogTitle, String message, int requestCode) {
        customDialog.setTitle(dialogTitle);
        customDialog.setMessage(message);
        customDialog.setNegativeButton(true);
        customDialog.setNegativeButtonText(getString(R.string.btn_negative_custom_dialog));
        customDialog.setPositiveButtonText(getString(R.string.btn_positive_custom_dialog));
        customDialog.setCloseIconVisibility(false);
        customDialog.setCustomDialogListener(new CustomDialog.CustomDialogListener() {
            @Override
            public void onPositiveButtonClick() {
                onConfirmationDialogPositiveButton(requestCode);
            }

            @Override
            public void onNegativeButtonClick() {
                onConfirmationDialogNegativeButton(requestCode);
            }
        });
        customDialog.show();
    }

    protected void showConfirmationDialog(String dialogTitle, String message, String btnPositive, String btnNegative, int requestCode) {
        customDialog.setTitle(dialogTitle);
        customDialog.setMessage(message);
        customDialog.setNegativeButton(true);
        customDialog.setNegativeButtonText(btnNegative);
        customDialog.setPositiveButtonText(btnPositive);
        customDialog.setCloseIconVisibility(true);
        customDialog.setCustomDialogListener(new CustomDialog.CustomDialogListener() {
            @Override
            public void onPositiveButtonClick() {
                onConfirmationDialogPositiveButton(requestCode);
            }

            @Override
            public void onNegativeButtonClick() {
                onConfirmationDialogNegativeButton(requestCode);
            }
        });
        customDialog.show();
    }

    public void showMessageDialog(String dialogTitle, String message, int requestCode) {
        customDialog.setTitle(dialogTitle);
        customDialog.setMessage(message);
        customDialog.setNegativeButton(false);
        customDialog.setNegativeButtonText(getString(R.string.btn_negative_custom_dialog));
        customDialog.setPositiveButtonText(getString(R.string.btn_ok_custom_dialog));
        customDialog.setCloseIconVisibility(false);
        customDialog.setCustomDialogListener(new CustomDialog.CustomDialogListener() {
            @Override
            public void onPositiveButtonClick() {

                onConfirmationDialogPositiveButton(requestCode);

            }

            @Override
            public void onNegativeButtonClick() {
                onConfirmationDialogNegativeButton(requestCode);
            }
        });
        customDialog.show();
    }

    /**
     * create instance of loading dialog
     */
    private void createDialog() {
        loadingDialog = new LoadingDialog(BaseActivity.this);
        loadingDialog.setCancelable(false);

        mViewModel.dialogMessage.observe(BaseActivity.this, msg -> {
            if (loadingDialog != null) {
                loadingDialog.setDialogMessage(String.valueOf(msg));
            }
        });

        mViewModel.dialogVisibility.observe(this, (showDialog) -> {

            if ((Boolean) showDialog) {
                if (loadingDialog != null)
                    loadingDialog.showDialog();
            } else {
                if (loadingDialog != null && loadingDialog.isShowing())
                    loadingDialog.dismissDialog();
            }
        });


    }

    public void setLoadingDialogMsg(String msg) {
        mViewModel.dialogMessage.setValue(msg);
    }

    public void setLoadingDialogVisibility(boolean visibility) {
        mViewModel.dialogVisibility.setValue(visibility);
    }

    /**
     * set toolbar
     *
     * @param toolbarBinding
     * @param showBackArrow
     * @param title
     */
    protected void setToolbar(ToolbarBinding toolbarBinding, boolean showBackArrow, String title) {
        setSupportActionBar(toolbarBinding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(showBackArrow);
        getSupportActionBar().setDisplayShowHomeEnabled(showBackArrow);
        getSupportActionBar().setHomeAsUpIndicator(ContextCompat.getDrawable(this, R.drawable.ic_back_arrow));
        this.toolbarBinding = toolbarBinding;
        setTitle(title);
    }

    public void setTitle(String title) {
        if (toolbarBinding != null) {
            toolbarBinding.txtToolbarTitle.setText(title);
        }
    }


    /**
     * call when custom dialog positive button clicked
     *
     * @param requestCode
     */
    protected void onConfirmationDialogPositiveButton(int requestCode) {

        if (Constants.DialogRequestCode.LOG_OUT == requestCode) {

            NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.cancelAll();

            SPManager.getInstance().logout();
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        } else {
            customDialogButtonClick.setValue(requestCode);
        }
    }

    /**
     * call when custom dialog negative clicked
     *
     * @param requestCode
     */
    protected void onConfirmationDialogNegativeButton(int requestCode) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;

           /* case R.id.action_logout:
                showConfirmationDialog(getString(R.string.title_logout), getString(R.string.msg_logout), Constants.DialogRequestCode.LOG_OUT);
                break;*/


        }
        return super.onOptionsItemSelected(item);
    }

    protected void logOut() {
        showConfirmationDialog(getString(R.string.title_logout), getString(R.string.msg_logout), Constants.DialogRequestCode.LOG_OUT);
    }

    /**
     * Overrides the pending Activity transition by performing the "Enter" animation.
     */
    protected void overridePendingTransitionEnter() {
        overridePendingTransition(R.anim.anim_slide_in_from_right_enter, R.anim.anim_slide_in_from_right_exit);
    }

    /**
     * Overrides the pending Activity transition by performing the "Exit" animation.
     */
    protected void overridePendingTransitionExit() {
        overridePendingTransition(R.anim.anim_slide_in_from_left_enter, R.anim.anim_slide_in_from_left_exit);
    }


    /**
     * start activity with animation
     *
     * @param intent
     */
    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransitionEnter();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransitionExit();
    }

    @Override
    public void supportFinishAfterTransition() {
        super.supportFinishAfterTransition();
        overridePendingTransitionExit();
    }


    /**
     * Replace fragment with animation
     *
     * @param fragment
     * @param isAddToStack
     * @param container
     */
    protected void replaceFragmentWithAnimation(Fragment fragment, boolean isAddToStack, int container) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.anim_slide_in_from_right_enter, R.anim.anim_slide_in_from_right_exit, R.anim.anim_slide_in_from_left_enter, R.anim.anim_slide_in_from_left_exit);
        transaction.replace(container, fragment);
        if (isAddToStack) {
            transaction.addToBackStack(fragment.getClass().getSimpleName());
        }
        transaction.commit();
    }

    /**
     * Replace fragment with animation
     *
     * @param fragment
     * @param isAddToStack
     * @param container
     */
    protected void replaceFragmentWithAnimation(Fragment parentFragment, Fragment fragment, boolean isAddToStack, int container) {
        FragmentTransaction transaction = parentFragment.getChildFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.anim_slide_in_from_right_enter, R.anim.anim_slide_in_from_right_exit, R.anim.anim_slide_in_from_left_enter, R.anim.anim_slide_in_from_left_exit);
        transaction.replace(container, fragment);
        if (isAddToStack) {
            transaction.addToBackStack(fragment.getClass().getSimpleName());
        }
        transaction.commit();
    }


}