package com.ganesh.smartwatermeter.view.home;


import com.ganesh.smartwatermeter.common.base.BaseViewModel;
import com.ganesh.smartwatermeter.model.DrawerMenuModel;

public class HomeViewModel extends BaseViewModel<HomeNavigator> {

    private int currentMenuId;


    public int getCurrentMenuId() {
        return currentMenuId;
    }

    public void setCurrentMenuId(int currentMenuId) {
        this.currentMenuId = currentMenuId;
    }

    public void onMenuSelect(DrawerMenuModel drawerMenuModel) {
        mNavigator.onMenuSelected(drawerMenuModel);
    }

    public void onSignOut() {
        mNavigator.onSignOut();
    }




}
