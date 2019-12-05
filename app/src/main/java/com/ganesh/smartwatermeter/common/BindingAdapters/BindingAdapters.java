package com.ganesh.smartwatermeter.common.BindingAdapters;

import android.graphics.Bitmap;
import android.text.TextUtils;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.ganesh.smartwatermeter.BuildConfig;
import com.ganesh.smartwatermeter.R;
import com.ganesh.smartwatermeter.common.utils.Constants;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.databinding.BindingAdapter;

public class BindingAdapters {

    @BindingAdapter({"imgFileName"})
    public static void setProfilePic(AppCompatImageView imageView, String imgProfile) {

        if(!TextUtils.isEmpty(imgProfile)){
            imgProfile=imgProfile.replaceAll(".jpg","");
        }

        Glide.with(imageView.getContext()).load(BuildConfig.BASE_URL + BuildConfig.IMAGE_PATH + imgProfile + ".jpg")
                .asBitmap()
                .skipMemoryCache(true)
                .placeholder(R.drawable.ic_avatar)
                .centerCrop().into(new BitmapImageViewTarget(imageView) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(imageView.getContext().getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                imageView.setImageDrawable(circularBitmapDrawable);
            }
        });

    }

    @BindingAdapter({"menuIcon"})
    public static void setIcon(AppCompatImageView imageView, int menuIcon) {
        imageView.setImageResource(menuIcon);
    }

    @BindingAdapter({"paymentStatus"})
    public static void setInvoiceStatus(AppCompatTextView appCompatTextView, int status) {
        switch (status) {
            case Constants.PaymentStatus.UNPAID:
                appCompatTextView.setText(appCompatTextView.getContext().getString(R.string.status_unpaid));
                break;

            case Constants.PaymentStatus.PAID:
                appCompatTextView.setText(appCompatTextView.getContext().getString(R.string.status_paid));
                break;

            case Constants.PaymentStatus.UNPAYABLE:
                appCompatTextView.setText(appCompatTextView.getContext().getString(R.string.status_unpayable));
                break;
        }
    }
}
