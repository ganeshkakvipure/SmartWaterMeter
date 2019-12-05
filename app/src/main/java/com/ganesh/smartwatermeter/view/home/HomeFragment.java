package com.ganesh.smartwatermeter.view.home;


import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;

import com.ganesh.smartwatermeter.R;
import com.ganesh.smartwatermeter.common.base.BaseFragment;
import com.ganesh.smartwatermeter.common.base.BaseNavigator;
import com.ganesh.smartwatermeter.common.utils.Utils;
import com.ganesh.smartwatermeter.databinding.FragmentHomeBinding;
import com.ganesh.smartwatermeter.model.CustomerModel;

import androidx.lifecycle.Observer;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class HomeFragment extends BaseFragment<FragmentHomeBinding, DashboardViewModel> implements BaseNavigator {

    private static final String TAG = HomeFragment.class.getSimpleName();

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public int getLayoutID() {
        return R.layout.fragment_home;
    }

    @Override
    public void onBinding() {

        startAnimation();
        mBinding.swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mViewModel.getReading();
            }
        });

        mViewModel.getCustomerModelMutableLiveData().observe(this, new Observer<CustomerModel>() {
            @Override
            public void onChanged(CustomerModel customerModel) {
                mBinding.swipeContainer.setRefreshing(false);
                mBinding.setModel(customerModel);
            }
        });

        mViewModel.init();
        mViewModel.getReading();
    }


    @Override
    public Class getViewModel() {
        return DashboardViewModel.class;
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

    private void startAnimation() {

        RotateAnimation rotateAnim = new RotateAnimation(0, 360,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        rotateAnim.setDuration(3000);
        rotateAnim.setRepeatCount(Animation.INFINITE);
        rotateAnim.setFillAfter(true);
        rotateAnim.setInterpolator(new LinearInterpolator());
        rotateAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                // we empty the result text view when the animation start
                //resultTv.setText("");
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // we display the correct sector pointed by the triangle at the end of the rotate animation
                //resultTv.setText(getSector(360 - (degree % 360)));
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        // we start the animation
        mBinding.imgRotation.startAnimation(rotateAnim);
    }


}
