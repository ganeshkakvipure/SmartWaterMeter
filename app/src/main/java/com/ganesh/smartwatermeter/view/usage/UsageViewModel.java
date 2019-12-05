package com.ganesh.smartwatermeter.view.usage;

import com.ganesh.smartwatermeter.common.base.BaseNavigator;
import com.ganesh.smartwatermeter.common.base.BaseViewModel;
import com.ganesh.smartwatermeter.common.repository.DataRepository;
import com.ganesh.smartwatermeter.model.UsagesModel;

import androidx.databinding.ObservableArrayList;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.functions.Consumer;

public class UsageViewModel extends BaseViewModel<BaseNavigator> {

    private MutableLiveData<ObservableArrayList<UsagesModel>> usageList = new MutableLiveData<>();

    public MutableLiveData<ObservableArrayList<UsagesModel>> getUsageList() {
        return usageList;
    }


    public void getUsageData() {
        dialogMessage.setValue("Loading...");
        dialogVisibility.setValue(true);
        compositeDisposable.add(DataRepository.getInstance().getUsageData(new Consumer<ObservableArrayList<UsagesModel>>() {
            @Override
            public void accept(ObservableArrayList<UsagesModel> usagesModels) throws Exception {
                dialogVisibility.setValue(false);
                usageList.setValue(usagesModels);

            }
        }, this::checkError));

    }
}
