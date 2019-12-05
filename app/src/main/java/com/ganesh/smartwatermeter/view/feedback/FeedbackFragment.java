package com.ganesh.smartwatermeter.view.feedback;


import com.ganesh.smartwatermeter.R;
import com.ganesh.smartwatermeter.common.base.BaseFragment;
import com.ganesh.smartwatermeter.common.base.BaseNavigator;
import com.ganesh.smartwatermeter.common.utils.Constants;
import com.ganesh.smartwatermeter.common.utils.SPManager;
import com.ganesh.smartwatermeter.common.utils.Utils;
import com.ganesh.smartwatermeter.databinding.FragmentFeedbackBinding;
import com.ganesh.smartwatermeter.model.request.SendFeedbackRequestModel;

import androidx.appcompat.app.AlertDialog;

public class FeedbackFragment extends BaseFragment<FragmentFeedbackBinding, FeedbackViewModel> implements FeedbackNavigator {

    private static final String TAG = FeedbackFragment.class.getSimpleName();

    public static FeedbackFragment newInstance() {
        return new FeedbackFragment();
    }

    @Override
    public int getLayoutID() {
        return R.layout.fragment_feedback;
    }

    @Override
    public void onBinding() {
        mBinding.setModel(new SendFeedbackRequestModel(SPManager.getInstance().getUserName()));
        mBinding.setViewModel(mViewModel);
        mViewModel.setRemarkType(new String[]{"Better", "Good", "Sad" });
        mViewModel.getFeedbackType();
    }

    @Override
    public Class getViewModel() {
        return FeedbackViewModel.class;
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
    public void onError(String errorMessage) {
        Utils.getInstance().showErrorMsg(mContext, errorMessage);
    }

    @Override
    public void onNoInternetConnection() {
        Utils.getInstance().showNoInternetMsg(mContext);
    }

    @Override
    public void onTypeSelect() {
        AlertDialog.Builder b = new AlertDialog.Builder(mContext);
        b.setTitle(getString(R.string.select_feedback_type));
        String[] types = mViewModel.getRemarkType();
        b.setItems(types, (dialog, which) -> {
            dialog.dismiss();
            mBinding.getModel().setType(types[which]);

        });

        b.show();
    }

    @Override
    public void onFeedbackSend() {
        mBinding.setModel(new SendFeedbackRequestModel(SPManager.getInstance().getUserName()));
        showMessageDialog(getString(R.string.feedback_send_success_title), getString(R.string.feedback_send_success_msg), Constants.DialogRequestCode.SEND_FEEDBACK);
    }


}
