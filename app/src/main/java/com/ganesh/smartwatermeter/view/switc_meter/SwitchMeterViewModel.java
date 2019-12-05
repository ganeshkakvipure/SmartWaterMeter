package com.ganesh.smartwatermeter.view.switc_meter;

import com.ganesh.smartwatermeter.common.base.BaseViewModel;

public class SwitchMeterViewModel extends BaseViewModel<SwitchMeterNavigator> {

    public String currentMeterId;

    public String selectedMeter;

    public void setCurrentMeterId(String currentMeterId) {
        this.currentMeterId = currentMeterId;
    }

    public void onMeterSelect(String meterId) {

        if (!meterId.equalsIgnoreCase(currentMeterId)) {
            selectedMeter = meterId;
            mNavigator.askConfirmationForMeterSwitch();
        }
    }
}
