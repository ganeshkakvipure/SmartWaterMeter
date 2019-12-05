package com.ganesh.smartwatermeter.view.feedback;


import android.text.TextUtils;

import com.ganesh.smartwatermeter.common.base.BaseViewModel;
import com.ganesh.smartwatermeter.common.repository.DataRepository;
import com.ganesh.smartwatermeter.model.request.SendFeedbackRequestModel;
import com.ganesh.smartwatermeter.model.response.BaseResponse;

import io.reactivex.functions.Consumer;

public class FeedbackViewModel extends BaseViewModel<FeedbackNavigator> {

    private String[] remarkType;

    public String[] getRemarkType() {
        return remarkType;
    }

    public void setRemarkType(String[] remarkType) {
        this.remarkType = remarkType;
    }

    public void onTypeSelect() {
        mNavigator.onTypeSelect();
    }

    public void onSendFeedback(SendFeedbackRequestModel sendFeedbackRequestModel) {
        if (isValid(sendFeedbackRequestModel)) {

            dialogMessage.setValue("Sending...");
            dialogVisibility.setValue(true);
            compositeDisposable.add(DataRepository.getInstance().sendFeedback(sendFeedbackRequestModel, new Consumer<BaseResponse>() {
                @Override
                public void accept(BaseResponse baseResponse) throws Exception {
                    dialogVisibility.setValue(false);
                    mNavigator.onFeedbackSend();
                }
            }, this::checkError));
        }
    }

    public void getFeedbackType() {
        dialogMessage.setValue("Loading...");
        dialogVisibility.setValue(true);
        compositeDisposable.add(DataRepository.getInstance().getFeedbackType(feedbackTypes -> {
            dialogVisibility.setValue(false);
            if (feedbackTypes != null) {
                setRemarkType(feedbackTypes);
            }
        }, this::checkError));
    }

    private boolean isValid(SendFeedbackRequestModel sendFeedbackRequestModel) {
        if (TextUtils.isEmpty(sendFeedbackRequestModel.getType())) {
            mNavigator.onError("Please select type");
            return false;
        } else if (TextUtils.isEmpty(sendFeedbackRequestModel.getComments().trim())) {
            mNavigator.onError("Please enter comment");
            return false;
        }

        return true;
    }
}
