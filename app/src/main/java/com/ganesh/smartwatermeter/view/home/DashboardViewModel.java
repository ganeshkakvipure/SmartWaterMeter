package com.ganesh.smartwatermeter.view.home;

import com.ganesh.smartwatermeter.common.base.BaseNavigator;
import com.ganesh.smartwatermeter.common.base.BaseViewModel;
import com.ganesh.smartwatermeter.common.repository.DataRepository;
import com.ganesh.smartwatermeter.common.utils.SPManager;
import com.ganesh.smartwatermeter.model.CustomerModel;

import androidx.lifecycle.MutableLiveData;
import io.reactivex.functions.Consumer;

public class DashboardViewModel extends BaseViewModel<BaseNavigator> {

    public MutableLiveData<CustomerModel> customerModelMutableLiveData=new MutableLiveData<>();

    public MutableLiveData<CustomerModel> getCustomerModelMutableLiveData() {
        return customerModelMutableLiveData;
    }

    public void init (){
        CustomerModel customerModel= SPManager.getInstance().getCustomerData();
        customerModel.setMeterId(SPManager.getInstance().getMeterId());
        customerModelMutableLiveData.setValue(customerModel);
    }

    public void getReading(){

        compositeDisposable.add(DataRepository.getInstance().getMeterReading(new Consumer<CustomerModel>() {
            @Override
            public void accept(CustomerModel customerModel) throws Exception {
                CustomerModel customerModel1 = getCustomerModelMutableLiveData().getValue();
                assert customerModel1 != null;
                customerModel1.setMeterReading(customerModel.getMeterReading());
                customerModel1.setLastSyncTime(customerModel.getLastSyncTime());
                customerModel1.setMonthlyUsage(customerModel.getMonthlyUsage());
                SPManager.getInstance().setCustomerName(customerModel1);
                customerModelMutableLiveData.setValue(customerModel1);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                checkError(throwable);
            }
        }));
    }
}
