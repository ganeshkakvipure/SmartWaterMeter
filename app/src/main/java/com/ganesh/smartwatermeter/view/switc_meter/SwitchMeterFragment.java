package com.ganesh.smartwatermeter.view.switc_meter;

import com.ganesh.smartwatermeter.R;
import com.ganesh.smartwatermeter.common.base.BaseFragment;
import com.ganesh.smartwatermeter.common.base.BaseNavigator;
import com.ganesh.smartwatermeter.common.base.GenericAdapter;
import com.ganesh.smartwatermeter.common.utils.Constants;
import com.ganesh.smartwatermeter.common.utils.SPManager;
import com.ganesh.smartwatermeter.common.utils.Utils;
import com.ganesh.smartwatermeter.databinding.FragmentSwitchMeterBinding;
import com.ganesh.smartwatermeter.databinding.ItemSwitchMeterBinding;
import com.ganesh.smartwatermeter.model.CustomerModel;
import com.ganesh.smartwatermeter.view.home.HomeActivity;

import java.util.ArrayList;
import java.util.Objects;

import androidx.recyclerview.widget.LinearLayoutManager;

public class SwitchMeterFragment extends BaseFragment<FragmentSwitchMeterBinding, SwitchMeterViewModel> implements SwitchMeterNavigator {

    private static final String TAG = SwitchMeterFragment.class.getSimpleName();

    public static SwitchMeterFragment newInstance() {
        return new SwitchMeterFragment();
    }

    @Override
    public int getLayoutID() {
        return R.layout.fragment_switch_meter;
    }

    @Override
    public void onBinding() {
        mViewModel.setCurrentMeterId(SPManager.getInstance().getMeterId());
        CustomerModel customerModel = SPManager.getInstance().getCustomerData();
        bindMeterList(customerModel.getMeters());
    }

    private void bindMeterList(ArrayList<String> meters) {

        GenericAdapter<String, ItemSwitchMeterBinding> adapter = new GenericAdapter<String, ItemSwitchMeterBinding>(mContext) {
            @Override
            public int getLayoutId() {
                return R.layout.item_switch_meter;
            }

            @Override
            public void onBindView(ItemSwitchMeterBinding binding, String item, int position) {
                binding.setViewModel(mViewModel);
                binding.setModel(item);

            }
        };

        mBinding.rvMeter.setLayoutManager(new LinearLayoutManager(mContext));
        mBinding.rvMeter.setAdapter(adapter);
        adapter.updateData(meters);
    }

    @Override
    public Class getViewModel() {
        return SwitchMeterViewModel.class;
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
    public void askConfirmationForMeterSwitch() {
        showConfirmationDialog("Meter Switch", "Do you want switch meter?", Constants.DialogRequestCode.ON_METER_SWITCH);
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
    protected void onConfirmationDialogPositiveButton(int requestCode) {

        switch (requestCode) {
            case Constants.DialogRequestCode.ON_METER_SWITCH:
                SPManager.getInstance().setMeterId(mViewModel.selectedMeter);
                onClearConfirmationDialogPositiveButton();
                ((HomeActivity) Objects.requireNonNull(getActivity())).refreshHomeScreen();
                break;


        }
    }
}
