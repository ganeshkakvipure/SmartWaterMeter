package com.ganesh.smartwatermeter.common.utils;

import android.os.Environment;

import java.io.File;

/**
 * Created by Ganesh on 23/11/17.
 */

public class Constants {

    public final static String FILE_PATH = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + File.separator + "SmartWater";
    public static String DateFormatForAPI = "yyyy-MM-dd HH:mm:ss";

    public interface IntentExtra {
        String REMARK_TYPES = "remark_types";
        String UPDATE_TYPE = "update_type";
        String UPDATE_MSG = "update_msg";


    }

    public interface COME_FROM {

    }

    public interface PaymentStatus{
        int UNPAID=0;
        int PAID =1;
        int UNPAYABLE=2;
    }


    public interface DialogRequestCode {
        int LOG_OUT = 1001;
        int NO_INTERNET = 1002;
        int ON_ERROR = 1003;
        int SEND_FEEDBACK = 1004;
        int ON_METER_SWITCH = 1005;
        int OPEN_INVOICE_PDF = 1006;


    }

    public interface AppVersioning {
        String FORCE = "1";
        String OPTIONAL = "0";
        String NO_UPDATE = "2";
    }

    public interface MenuItemId {
        int DASHBOARD = 1;
        int USAGE_HISTORY = 2;
        int BILL_DETAILS = 3;
        int SWITCH_METER = 4;
        int FEEDBACK = 5;
        int CONTACT_US = 6;
    }

    public class PermissionRquestCode {
        public static final int STORAGE = 101;
    }
}