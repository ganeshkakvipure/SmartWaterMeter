package com.ganesh.smartwatermeter.common.utils;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ganesh.smartwatermeter.R;
import com.ganesh.smartwatermeter.common.base.BaseActivity;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by Ganesh on 23/11/2017.
 */

public class Utils {


    static Utils instance;

    public static Utils getInstance() {
        if (instance == null) {
            instance = new Utils();
        }
        return instance;
    }

    public static int getColorFromHex(String colorCode) {
        return Color.parseColor(!TextUtils.isEmpty(colorCode) ? colorCode : "#4a9bad");


    }

    public boolean isValidMobile(String phone) {
        boolean check = false;
        if (!TextUtils.isEmpty(phone)) {
            if (!Pattern.matches("[a-zA-Z]+", phone)) {
                if (phone.length() == 10) {
                    check = true;
                } else {
                    check = false;
                }
            } else {
                check = false;
            }
        }
        return check;
    }

    public String getFormatedDate(String format) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(cal.getTime());
    }

    @SuppressLint("MissingPermission")
    public String getDeviceId(Context context) {
        String deviceId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        if (deviceId == null) {
            deviceId = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
        }
        return deviceId;
    }

    public String getTimeFormatted(long time) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(time);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:MM:SS a");
        return sdf.format(cal.getTime());
    }

    public double parsePriceValue(String price) {
        double resPrice = 0.0;
        try {
            resPrice = Double.parseDouble(getNotNullString(price));
        } catch (Exception ex) {

        }
        return resPrice;
    }


    public double parseQtyValue(String price) {
        double resPrice = 0.0;
        try {
            resPrice = Double.parseDouble(getNotNullString(price));
        } catch (Exception ex) {

        }
        return parseQtyValue(resPrice);
    }


    public double parseQtyValue(double price) {

        return Double.parseDouble(new DecimalFormat("##.###").format(price));
    }


    public float parseFloatValue(String price) {
        float resPrice = 0.0f;
        try {
            resPrice = Float.parseFloat(getNotNullString(price));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return resPrice;
    }

    public int parseIntValue(String value) {
        int resPrice = 0;
        try {
            resPrice = Integer.parseInt(getNotNullString(value));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return resPrice;
    }

    public String getNotNullString(String value) {
        return value == null ? "" : value.trim();
    }

    public int getValidInt(String valule) {
        int res = 0;
        try {
            res = Integer.parseInt(getNotNullString(valule));
        } catch (Exception ex) {
            res = 0;
        }
        return res;
    }

    public String getIPAddress(boolean useIPv4) {
        try {
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface intf : interfaces) {
                List<InetAddress> addrs = Collections.list(intf.getInetAddresses());
                for (InetAddress addr : addrs) {
                    if (!addr.isLoopbackAddress()) {
                        String sAddr = addr.getHostAddress();
                        //boolean isIPv4 = InetAddressUtils.isIPv4Address(sAddr);
                        boolean isIPv4 = sAddr.indexOf(':') < 0;

                        if (useIPv4) {
                            if (isIPv4)
                                return sAddr;
                        } else {
                            if (!isIPv4) {
                                int delim = sAddr.indexOf('%'); // drop ip6 zone suffix
                                return delim < 0 ? sAddr.toUpperCase() : sAddr.substring(0, delim).toUpperCase();
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) {
        } // for now eat exceptions
        return "";
    }

    public void playAudio(Context context, int id) {
        AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC), 0);

        MediaPlayer mediaPlayer = MediaPlayer.create(context, id);
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.start();
            }
        });
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mediaPlayer.release();
            }
        });
    }

    public boolean isNetworkAvailable(Context context) {

        if(context == null)  return false;
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager != null) {
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                NetworkCapabilities capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());
                if (capabilities != null) {
                    if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                        return true;
                    } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                        return true;
                    }  else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)){
                        return true;
                    }
                }
            } else {

                try {
                    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
                    if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
                        Log.i("update_statut", "Network is available : true");
                        return true;
                    }
                } catch (Exception e) {
                    Log.i("update_statut", "" + e.getMessage());
                }
            }
        }
        Log.i("update_statut","Network is available : FALSE ");
        return false;
    }





    public void hideKeypad(Context mContext) {
        View view = ((BaseActivity) mContext).getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public void showDialogWhenException(Context context) {
        Toast.makeText(context, context.getString(R.string.server_error), Toast.LENGTH_SHORT).show();
    }

    public void showNoInternetMsg(Context mContext) {
        if (mContext instanceof BaseActivity) {
            ((BaseActivity) mContext).showMessageDialog(mContext.getString(R.string.no_internet_connection),
                    mContext.getString(R.string.no_internet_connection_msg), Constants.DialogRequestCode.NO_INTERNET);
        } else {
            Toast.makeText(mContext, mContext.getString(R.string.no_internet_connection_msg), Toast.LENGTH_SHORT).show();
        }
    }

    public void showFailureMsg(Context context) {
        Toast.makeText(context, context.getString(R.string.failed_con_server), Toast.LENGTH_SHORT).show();
    }

    public void showErrorMsg(Context mContext, String msg) {
        if (mContext instanceof BaseActivity) {
            ((BaseActivity) mContext).showMessageDialog(mContext.getString(R.string.error_title), msg, Constants.DialogRequestCode.ON_ERROR);
        } else {
            Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
        }
    }

    public void showMessage(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public long getTimeStamp() {
        Long tsLong = System.currentTimeMillis();
        return tsLong;
    }

    public int getParceInt(String string) {
        int qty = 0;
        try {
            qty = Integer.parseInt(getNotNullString(string));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return qty;
    }

    public String parseIntToBoolenString(int flag) {
        return flag == 1 ? "true" : "false";
    }


    public long getDiffDays(String dateString, String shelfLifeLimit) {
        long days = 0;
        SimpleDateFormat sdf = new SimpleDateFormat(Constants.DateFormatForAPI);
        try {

            Date sourceDate = sdf.parse(dateString);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(sourceDate);
            calendar.add(Calendar.DATE, parseIntValue(shelfLifeLimit));
            sourceDate = new Date(calendar.getTimeInMillis());
            /** Today's date */
            Date today = new Date();
            // Get msec from each, and subtract.
            long diff = sourceDate.getTime() - today.getTime();
            days = diff / (1000 * 60 * 60 * 24);

        } catch (Exception e) {
            e.printStackTrace();
        }
        /** The date at the end of the last century ***/
        return days;
    }

    public void setAmountFilter(EditText edt, final int maxDigitsBeforeDecimalPoint, final int maxDigitsAfterDecimalPoint) {
        InputFilter[] filterArray = new InputFilter[2];
        InputFilter filter = new InputFilter() {

            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                StringBuilder builder = new StringBuilder(dest);
                builder.replace(dstart, dend, source.subSequence(start, end).toString());
                if (!builder.toString().matches(
                        "(([1-9]{1})([0-9]{0," + (maxDigitsBeforeDecimalPoint - 1) + "})?)?(\\.[0-9]{0," + maxDigitsAfterDecimalPoint + "})?"

                )) {
                    if (source.length() == 0)
                        return dest.subSequence(dstart, dend);
                    return "";
                }

                return null;
            }
        };
        filterArray[0] = filter;
        filterArray[1] = new InputFilter.LengthFilter(maxDigitsBeforeDecimalPoint);
        edt.setFilters(filterArray);
    }

    public void setAmountFilter(TextView txt, int maxDigitsBeforeDecimalPoint, int maxDigitsAfterDecimalPoint) {
        InputFilter[] filterArray = new InputFilter[2];
        InputFilter filter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                StringBuilder builder = new StringBuilder(dest);
                builder.replace(dstart, dend, source.subSequence(start, end).toString());
                if (!builder.toString().matches(
                        "(([1-9]{1})([0-9]{0," + (maxDigitsBeforeDecimalPoint - 1) + "})?)?(\\.[0-9]{0," + maxDigitsAfterDecimalPoint + "})?"

                )) {
                    if (source.length() == 0)
                        return dest.subSequence(dstart, dend);
                    return "";
                }

                return null;
            }
        };
        filterArray[0] = filter;
        filterArray[1] = new InputFilter.LengthFilter(maxDigitsBeforeDecimalPoint);
        txt.setFilters(filterArray);
    }




}
