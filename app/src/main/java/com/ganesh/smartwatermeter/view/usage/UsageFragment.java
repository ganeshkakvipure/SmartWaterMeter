package com.ganesh.smartwatermeter.view.usage;

import com.ganesh.smartwatermeter.R;
import com.ganesh.smartwatermeter.common.base.BaseFragment;
import com.ganesh.smartwatermeter.common.base.BaseNavigator;
import com.ganesh.smartwatermeter.common.base.GenericAdapter;
import com.ganesh.smartwatermeter.common.utils.Utils;
import com.ganesh.smartwatermeter.databinding.FragmentUsageBinding;
import com.ganesh.smartwatermeter.databinding.ItemUsageBinding;
import com.ganesh.smartwatermeter.model.UsagesModel;

import androidx.databinding.ObservableArrayList;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

public class UsageFragment extends BaseFragment<FragmentUsageBinding,UsageViewModel> implements BaseNavigator{

    private static final String TAG = UsageFragment.class.getSimpleName();

    public static  UsageFragment newInstance(){
        return new UsageFragment();
    }

    @Override
    public int getLayoutID() {
        return R.layout.fragment_usage;
    }

    @Override
    public void onBinding() {

        GenericAdapter<UsagesModel, ItemUsageBinding> adapter=new GenericAdapter<UsagesModel, ItemUsageBinding>(mContext) {
            @Override
            public int getLayoutId() {
                return R.layout.item_usage;
            }

            @Override
            public void onBindView(ItemUsageBinding binding, UsagesModel item, int position) {
                binding.setModel(item);
            }
        };
        mBinding.rvUsage.setLayoutManager(new LinearLayoutManager(mContext));
        mBinding.rvUsage.setAdapter(adapter);

        mViewModel.getUsageList().observe(this, new Observer<ObservableArrayList<UsagesModel>>() {
            @Override
            public void onChanged(ObservableArrayList<UsagesModel> usagesModels) {
                adapter.updateData(usagesModels);
            }
        });

        mViewModel.getUsageData();
    }

    @Override
    public Class getViewModel() {
        return UsageViewModel.class;
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
        Utils.getInstance().showErrorMsg(mContext,errorMessage);
    }

    @Override
    public void onNoInternetConnection() {
        Utils.getInstance().showNoInternetMsg(mContext);
    }
}
