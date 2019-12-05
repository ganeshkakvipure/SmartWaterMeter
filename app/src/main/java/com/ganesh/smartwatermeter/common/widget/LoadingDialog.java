package com.ganesh.smartwatermeter.common.widget;


import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by Ganesh on 3/3/2018.
 */

public class LoadingDialog extends ProgressDialog {


    public LoadingDialog(Context context) {
        super(context);
    }


    public LoadingDialog setDialogMessage(String msg) {

        setMessage(msg);

        return this;
    }

    public void showDialog() {
        if (this != null && !this.isShowing())
            this.show();

    }

    public void dismissDialog() {
        if (this != null && this.isShowing())
            this.dismiss();
    }
}
