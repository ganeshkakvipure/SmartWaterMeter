package com.ganesh.smartwatermeter.common.base;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;


/**
 * Created by Ganesh on 7/7/17.
 */

public abstract class BaseFragment<T extends ViewDataBinding, V extends BaseViewModel> extends Fragment implements UICallbacks {

    protected Context mContext;
    protected T mBinding;
    protected V mViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, getLayoutID(), container, false);
        mViewModel = (V) ViewModelProviders.of(this).get(getViewModel());
        mViewModel.setNavigator(getNavigatorReference());
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mContext = getContext();
        createDialog();
        onBinding();

    }

    public void setTitle(String title) {
        getBaseActivity().setTitle(title);
    }

    protected BaseActivity<ViewDataBinding, BaseViewModel> getBaseActivity() {
        return (BaseActivity<ViewDataBinding, BaseViewModel>) getActivity();
    }

    protected void showMessageDialog(String dialogTitle, String message, int requestCode) {
        getBaseActivity().showMessageDialog(dialogTitle, message, requestCode);
    }

    protected void showConfirmationDialog(String dialogTitle, String message, int requestCode) {
        getBaseActivity().showConfirmationDialog(dialogTitle, message, requestCode);
    }

    /**
     * Replace fragment with animation
     *
     * @param fragment
     * @param isAddToStack
     * @param container
     */
    protected void replaceFragmentWithAnimation(Fragment fragment, boolean isAddToStack, int container) {
        getBaseActivity().replaceFragmentWithAnimation(fragment, isAddToStack, container);
    }

    protected void replaceFragmentWithAnimation(Fragment parentFragment, Fragment fragment, boolean isAddToStack, int container) {
        getBaseActivity().replaceFragmentWithAnimation(parentFragment, fragment, isAddToStack, container);
    }

    private void createDialog() {
        mViewModel.dialogMessage.observe(this, msg -> {
            getBaseActivity().setLoadingDialogMsg(String.valueOf(msg));
        });

        mViewModel.dialogVisibility.observe(this, (showDialog) -> {
            getBaseActivity().setLoadingDialogVisibility((Boolean) showDialog);
        });

        getBaseActivity().getCustomDialogButtonClick().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer requestCode) {
                onConfirmationDialogPositiveButton(requestCode);
            }
        });
    }

    public void onClearConfirmationDialogPositiveButton() {
        getBaseActivity().getCustomDialogButtonClick().setValue(-1);
    }

    /**
     * call when custom dialog positive button clicked
     *
     * @param requestCode
     */
    protected void onConfirmationDialogPositiveButton(int requestCode) {

    }
}
