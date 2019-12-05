package com.ganesh.smartwatermeter.view.contact_us;

import com.ganesh.smartwatermeter.R;
import com.ganesh.smartwatermeter.common.base.BaseFragment;
import com.ganesh.smartwatermeter.common.base.BaseNavigator;
import com.ganesh.smartwatermeter.databinding.FragmentContactUsBinding;
import com.ganesh.smartwatermeter.model.ContactUsModel;

import androidx.lifecycle.Observer;

public class ContactUsFragment extends BaseFragment<FragmentContactUsBinding, ContactUsViewModel> {

    private static final String TAG = ContactUsFragment.class.getSimpleName();

    public static ContactUsFragment newInstance() {
        return new ContactUsFragment();
    }

    @Override
    public int getLayoutID() {
        return R.layout.fragment_contact_us;

    }

    @Override
    public void onBinding() {

        mViewModel.getContactUsModelMutableLiveData().observe(this, new Observer<ContactUsModel>() {
            @Override
            public void onChanged(ContactUsModel contactUsModel) {
                mBinding.setModel(contactUsModel);
            }
        });

        mViewModel.getContactUs();
    }

    @Override
    public Class getViewModel() {
        return ContactUsViewModel.class;
    }

    @Override
    public BaseNavigator getNavigatorReference() {
        return null;
    }

    @Override
    public String getScreenName() {
        return TAG;
    }
}
