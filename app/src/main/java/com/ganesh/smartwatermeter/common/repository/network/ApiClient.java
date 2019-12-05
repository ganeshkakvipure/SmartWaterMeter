package com.ganesh.smartwatermeter.common.repository.network;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.ganesh.smartwatermeter.BuildConfig;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by Ganesh on 23/05/17.
 */
public class ApiClient {

    public static final String PATH_URL = BuildConfig.PATH_URL;
    private static final String BASE_URL = BuildConfig.BASE_URL;/*"http://10.202.3.152:9000";*///

    private static Retrofit retrofit = null;


    public static ApiInterface getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(getOkkHttpClient())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()))
                    .build();
        }
        return retrofit.create(ApiInterface.class);
    }


    private static OkHttpClient getOkkHttpClient() {
        return new OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(new HeaderInterceptor())
                .addNetworkInterceptor(new StethoInterceptor())
                .build();
    }

    /**
     * Web service method name
     */

    public interface ApiMethod {
        String LOGIN_USER = "login.php";
        String GET_USAGE_HISTORY = "usage.php";
        String GET_INVOICE_LIST = "bills.php";
        String GET_METER_READING = "refresh.php";
        String SEND_FEEDBACK = "feedback.php";
        String FEEDBACK_TYPE = "feedback_type.php";
        String GET_CONTACT_US = "contacts.php";
        String APP_VERSION_CONTROL = "version_control.php";
    }

    /**
     * Created by Ganesh on 19/6/17.
     */

    public static class HeaderInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain)
                throws IOException {
            Request request = chain.request();
            request = request.newBuilder()
                    .addHeader("Content-Type", "application/json;charset=utf-8")
                    .addHeader("Accept", "application/json")
                    .build();
            Response response = chain.proceed(request);
            return response;
        }
    }
}
