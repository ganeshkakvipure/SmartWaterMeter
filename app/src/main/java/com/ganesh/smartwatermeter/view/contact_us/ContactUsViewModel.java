package com.ganesh.smartwatermeter.view.contact_us;

import com.ganesh.smartwatermeter.common.base.BaseNavigator;
import com.ganesh.smartwatermeter.common.base.BaseViewModel;
import com.ganesh.smartwatermeter.common.repository.DataRepository;
import com.ganesh.smartwatermeter.model.ContactUsModel;

import androidx.lifecycle.MutableLiveData;
import io.reactivex.functions.Consumer;

public class ContactUsViewModel extends BaseViewModel<BaseNavigator> {

    private MutableLiveData<ContactUsModel> contactUsModelMutableLiveData = new MutableLiveData<>();

    public MutableLiveData<ContactUsModel> getContactUsModelMutableLiveData() {
        return contactUsModelMutableLiveData;
    }

    public void getContactUs() {
        dialogMessage.setValue("Loading...");
        dialogVisibility.setValue(true);
        compositeDisposable.add(DataRepository.getInstance().getContactUs(new Consumer<ContactUsModel>() {
            @Override
            public void accept(ContactUsModel contactUsModel) throws Exception {
                contactUsModelMutableLiveData.setValue(contactUsModel);
                dialogVisibility.setValue(false);
            }
        }, this::checkError));
    }
}
