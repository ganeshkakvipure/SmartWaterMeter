package com.ganesh.smartwatermeter.view.home;


import com.ganesh.smartwatermeter.common.base.BaseNavigator;
import com.ganesh.smartwatermeter.model.DrawerMenuModel;

public interface HomeNavigator extends BaseNavigator {

    void onMenuSelected(DrawerMenuModel drawerMenuModel);

    void onSignOut();


}
