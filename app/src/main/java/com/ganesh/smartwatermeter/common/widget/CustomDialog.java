package com.ganesh.smartwatermeter.common.widget;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;

import com.ganesh.smartwatermeter.databinding.DialogCustomBinding;

import androidx.appcompat.app.AppCompatDialog;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

/**
 * Created by Ganesh on 3/7/2018.
 */

public class CustomDialog extends AppCompatDialog {

    private Context mContext;
    private DialogCustomBinding dialogCustomBinding;
    private CustomDialogListener customDialogListener;

    public CustomDialog(Context context) {
        super(context);
        mContext = context;
        dialogCustomBinding = DialogCustomBinding.inflate(LayoutInflater.from(mContext));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(dialogCustomBinding.getRoot());
        getWindow().setLayout(MATCH_PARENT, WRAP_CONTENT);
        setCancelable(false);


        dialogCustomBinding.txtNo.setOnClickListener(view -> {
            dismiss();
            if (customDialogListener != null) {
                customDialogListener.onNegativeButtonClick();
            }
        });
        dialogCustomBinding.txtYes.setOnClickListener(v -> {
            dismiss();
            if (customDialogListener != null) {
                customDialogListener.onPositiveButtonClick();
            }

        });

        dialogCustomBinding.close.setOnClickListener(v -> dismiss());

    }

    public void setNegativeButton(boolean isVisible) {
        if (isVisible) {
            dialogCustomBinding.txtNo.setVisibility(View.VISIBLE);
        } else {
            dialogCustomBinding.txtNo.setVisibility(View.GONE);
        }
    }

    public void setCloseIconVisibility(boolean isVisible) {
        if (isVisible) {
            dialogCustomBinding.close.setVisibility(View.VISIBLE);
        } else {
            dialogCustomBinding.close.setVisibility(View.GONE);
        }
    }

    public void setNegativeButtonText(String buttonText) {
        dialogCustomBinding.txtNo.setText(buttonText);
    }

    public void setPositiveButtonText(String buttonText) {
        dialogCustomBinding.txtYes.setText(buttonText);
    }


    public void setTitle(String dialogTitle) {
        dialogCustomBinding.title.setText(dialogTitle);
    }


    public void setMessage(String message) {
        dialogCustomBinding.message.setText(message);
    }

    public void setCustomDialogListener(CustomDialogListener customDialogListener) {
        this.customDialogListener = customDialogListener;
    }

    public interface CustomDialogListener {
        void onPositiveButtonClick();

        void onNegativeButtonClick();
    }
}
