package com.ganesh.smartwatermeter.common.repository.network;


import com.ganesh.smartwatermeter.model.ContactUsModel;
import com.ganesh.smartwatermeter.model.CustomerModel;
import com.ganesh.smartwatermeter.model.InvoiceDetailModel;
import com.ganesh.smartwatermeter.model.UsagesModel;
import com.ganesh.smartwatermeter.model.request.AppVersionUpdate;
import com.ganesh.smartwatermeter.model.request.LoginRequestModel;
import com.ganesh.smartwatermeter.model.request.SendFeedbackRequestModel;
import com.ganesh.smartwatermeter.model.request.UserNameRequestModel;
import com.ganesh.smartwatermeter.model.response.BaseResponse;
import com.ganesh.smartwatermeter.model.response.BaseResponseList;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Created by Ganesh on 23/05/17.
 */
public interface ApiInterface {

    @POST(ApiClient.PATH_URL + ApiClient.ApiMethod.LOGIN_USER)
    Observable<BaseResponse<CustomerModel>> authenticateUser(@Body LoginRequestModel loginRequestModel);

    @POST(ApiClient.PATH_URL + ApiClient.ApiMethod.GET_METER_READING)
    Observable<BaseResponse<CustomerModel>> getMeterReading(@Body UserNameRequestModel userNameRequestModel);

    @POST(ApiClient.PATH_URL + ApiClient.ApiMethod.GET_USAGE_HISTORY)
    Observable<BaseResponseList<UsagesModel>> getUsageData(@Body UserNameRequestModel userNameRequestModel);

    @POST(ApiClient.PATH_URL + ApiClient.ApiMethod.GET_INVOICE_LIST)
    Observable<BaseResponseList<InvoiceDetailModel>> getInvoiceList(@Body UserNameRequestModel userNameRequestModel);

    @POST(ApiClient.PATH_URL + ApiClient.ApiMethod.GET_CONTACT_US)
    Observable<BaseResponse<ContactUsModel>> getContactUs(@Body UserNameRequestModel userNameRequestModel);

    @POST(ApiClient.PATH_URL + ApiClient.ApiMethod.SEND_FEEDBACK)
    Observable<BaseResponse> sendFeedback(@Body SendFeedbackRequestModel sendFeedbackRequestModel);


    @POST(ApiClient.PATH_URL + ApiClient.ApiMethod.FEEDBACK_TYPE)
    Observable<BaseResponseList<String>> getFeedbackType();

    @Streaming
    @GET
    Observable<Response<ResponseBody>> downloadInvoice(@Url String fileUrl);

    @POST(ApiClient.PATH_URL + ApiClient.ApiMethod.APP_VERSION_CONTROL)
    Observable<BaseResponse> checkUpdate(@Body AppVersionUpdate appVersionUpdate);


}
