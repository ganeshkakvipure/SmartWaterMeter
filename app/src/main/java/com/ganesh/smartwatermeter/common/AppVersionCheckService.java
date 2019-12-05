package com.ganesh.smartwatermeter.common;


import android.content.Context;
import android.content.Intent;

import com.ganesh.smartwatermeter.common.repository.DataRepository;
import com.ganesh.smartwatermeter.common.utils.Constants;
import com.ganesh.smartwatermeter.model.response.BaseResponse;
import com.ganesh.smartwatermeter.view.update.AppUpdateDialogActivity;

import androidx.annotation.NonNull;
import androidx.core.app.JobIntentService;
import io.reactivex.functions.Consumer;

public class AppVersionCheckService extends JobIntentService {

    private static final String TAG = "MyJobIntentService";
    /**
     * Unique job ID for this service.
     */
    private static final int JOB_ID = 2;

    public static void enqueueWork(Context context, Intent intent) {
        enqueueWork(context, AppVersionCheckService.class, JOB_ID, intent);
    }

    @Override
    protected void onHandleWork(@NonNull Intent intent) {

        DataRepository.getInstance().checkUpdate(new Consumer<BaseResponse>() {
            @Override
            public void accept(BaseResponse baseResponse) throws Exception {

                switch (baseResponse.getUpdate()) {
                    case Constants.AppVersioning.FORCE:
                    case Constants.AppVersioning.OPTIONAL:
                        Intent intent1 = new Intent(AppVersionCheckService.this, AppUpdateDialogActivity.class);
                        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                        intent1.putExtra(Constants.IntentExtra.UPDATE_MSG, baseResponse.getResponseMessage());
                        intent1.putExtra(Constants.IntentExtra.UPDATE_TYPE, baseResponse.getUpdate());
                        startActivity(intent1);
                        break;
                }


            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {

            }
        });
    }
}
