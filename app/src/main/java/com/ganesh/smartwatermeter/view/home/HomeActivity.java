package com.ganesh.smartwatermeter.view.home;


import android.view.View;

import com.ganesh.smartwatermeter.R;
import com.ganesh.smartwatermeter.common.BindingAdapters.BindingAdapters;
import com.ganesh.smartwatermeter.common.base.BaseActivity;
import com.ganesh.smartwatermeter.common.base.BaseNavigator;
import com.ganesh.smartwatermeter.common.base.GenericAdapter;
import com.ganesh.smartwatermeter.common.utils.Constants;
import com.ganesh.smartwatermeter.common.utils.SPManager;
import com.ganesh.smartwatermeter.common.utils.Utils;
import com.ganesh.smartwatermeter.databinding.ActivityHomeBinding;
import com.ganesh.smartwatermeter.databinding.ItemDrawerMenuBinding;
import com.ganesh.smartwatermeter.model.CustomerModel;
import com.ganesh.smartwatermeter.model.DrawerMenuModel;
import com.ganesh.smartwatermeter.view.contact_us.ContactUsFragment;
import com.ganesh.smartwatermeter.view.feedback.FeedbackFragment;
import com.ganesh.smartwatermeter.view.invoice.InvoiceListFragment;
import com.ganesh.smartwatermeter.view.switc_meter.SwitchMeterFragment;
import com.ganesh.smartwatermeter.view.usage.UsageFragment;

import java.util.ArrayList;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.recyclerview.widget.LinearLayoutManager;

public class HomeActivity extends BaseActivity<ActivityHomeBinding, HomeViewModel> implements HomeNavigator {
    private static final String TAG = HomeActivity.class.getSimpleName();

    @Override
    public int getLayoutID() {
        return R.layout.activity_home;
    }

    @Override
    public void onBinding() {
        setToolbar(mBinding.containerHome.toolbar, false, getString(R.string.title_activity_dashboard));
        mBinding.setViewModel(mViewModel);
        refreshHomeScreen();
    }


    @Override
    public Class getViewModel() {
        return HomeViewModel.class;
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
    public void onMenuSelected(DrawerMenuModel drawerMenuModel) {
        mBinding.drawerLayout.closeDrawers();
        loadFragment(drawerMenuModel.getMenuId());
    }

    @Override
    public void onSignOut() {
        mBinding.drawerLayout.closeDrawers();
        logOut();
    }


    private void setDrawerHeader() {

        String userName = SPManager.getInstance().getUserName();

        BindingAdapters.setProfilePic(mBinding.navHeader.imgProfile, userName);
        CustomerModel customerModel = SPManager.getInstance().getCustomerData();
        mBinding.navHeader.txtName.setText(customerModel.getCustomerName());
        mBinding.navHeader.txtMeterId.setText(SPManager.getInstance().getMeterId());
    }

    private void setUpNavigatorDrawer() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        mBinding.navDrawerRecyclerView.setLayoutManager(linearLayoutManager);

        GenericAdapter<DrawerMenuModel, ItemDrawerMenuBinding> menuAdapter = new GenericAdapter<DrawerMenuModel, ItemDrawerMenuBinding>(mContext) {
            @Override
            public int getLayoutId() {
                return R.layout.item_drawer_menu;
            }

            @Override
            public void onBindView(ItemDrawerMenuBinding binding, DrawerMenuModel item, int position) {
                binding.setModel(item);
                binding.setViewHolder(mViewModel);
            }
        };
        mBinding.navDrawerRecyclerView.setAdapter(menuAdapter);
        menuAdapter.updateData(getMenuList());


        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, mBinding.drawerLayout, mBinding.containerHome.toolbar.toolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                super.onDrawerOpened(drawerView);
            }
        };

        //Setting the actionbarToggle to drawer layout
        mBinding.drawerLayout.addDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessary or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();
    }

    private ArrayList<DrawerMenuModel> getMenuList() {
        ArrayList<DrawerMenuModel> drawerMenuModels = new ArrayList<>();
        drawerMenuModels.add(new DrawerMenuModel(getString(R.string.title_activity_dashboard), R.drawable.ic_dashboard, Constants.MenuItemId.DASHBOARD));
        drawerMenuModels.add(new DrawerMenuModel(getString(R.string.title_activity_usage_history), R.drawable.ic_usage, Constants.MenuItemId.USAGE_HISTORY));
        drawerMenuModels.add(new DrawerMenuModel(getString(R.string.title_activity_bill_details), R.drawable.ic_bill, Constants.MenuItemId.BILL_DETAILS));
        CustomerModel customerModel = SPManager.getInstance().getCustomerData();
        if (customerModel.getMeters() != null && customerModel.getMeters().size() > 1) {
            drawerMenuModels.add(new DrawerMenuModel(getString(R.string.title_activity_switch_meter), R.drawable.ic_switch, Constants.MenuItemId.SWITCH_METER));
        }
        drawerMenuModels.add(new DrawerMenuModel(getString(R.string.title_activity_feedback), R.drawable.ic_feedback, Constants.MenuItemId.FEEDBACK));
        drawerMenuModels.add(new DrawerMenuModel(getString(R.string.title_activity_contact_us), R.drawable.ic_contact_us, Constants.MenuItemId.CONTACT_US));

        return drawerMenuModels;
    }

    private void loadFragment(int menuID) {

        mViewModel.setCurrentMenuId(menuID);

        switch (menuID) {
            case Constants.MenuItemId.DASHBOARD:
                setTitle(getString(R.string.title_activity_dashboard));
                replaceFragmentWithAnimation(HomeFragment.newInstance(), false, R.id.main_container);
                break;

            case Constants.MenuItemId.USAGE_HISTORY:
                setTitle(getString(R.string.title_activity_usage_history));
                replaceFragmentWithAnimation(UsageFragment.newInstance(), false, R.id.main_container);
                break;
            case Constants.MenuItemId.BILL_DETAILS:
                setTitle(getString(R.string.title_activity_bill_details));
                replaceFragmentWithAnimation(InvoiceListFragment.newInstance(), false, R.id.main_container);

                break;

            case Constants.MenuItemId.SWITCH_METER:
                setTitle(getString(R.string.title_activity_switch_meter));
                replaceFragmentWithAnimation(SwitchMeterFragment.newInstance(), false, R.id.main_container);
                break;

            case Constants.MenuItemId.FEEDBACK:
                setTitle(getString(R.string.title_activity_feedback));
                replaceFragmentWithAnimation(FeedbackFragment.newInstance(), false, R.id.main_container);

                break;

            case Constants.MenuItemId.CONTACT_US:
                setTitle(getString(R.string.title_activity_contact_us));
                replaceFragmentWithAnimation(ContactUsFragment.newInstance(), false, R.id.main_container);
                break;

        }
    }


    public void refreshHomeScreen() {
        setDrawerHeader();
        setUpNavigatorDrawer();
        loadFragment(Constants.MenuItemId.DASHBOARD);
    }


}
