package com.ganesh.smartwatermeter.model;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

public class DrawerMenuModel extends BaseObservable {

    private String menuName;
    private int menuIcon;
    private int menuId;

    public DrawerMenuModel(String menuName, int menuIcon, int menuId) {
        this.menuName = menuName;
        this.menuIcon = menuIcon;
        this.menuId = menuId;
    }

    @Bindable
    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
        notifyPropertyChanged(com.ganesh.smartwatermeter.BR.menuName);
    }

    @Bindable
    public int getMenuId() {
        return menuId;
    }

    public void setMenuId(int menuId) {
        this.menuId = menuId;
        notifyPropertyChanged(com.ganesh.smartwatermeter.BR.menuId);
    }

    @Bindable
    public int getMenuIcon() {
        return menuIcon;
    }

    public void setMenuIcon(int menuIcon) {
        this.menuIcon = menuIcon;
        notifyPropertyChanged(com.ganesh.smartwatermeter.BR.menuIcon);
    }
}
