package com.braintech.carpooling.common.requestresponse;

import android.content.Context;

import com.braintech.carpooling.BuildConfig;
import com.braintech.carpooling.common.helpers.NetworkHelper;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by Braintech on 6/10/2016.
 */
public class ApiAdapter {

    private static ApiService sInstance;

    public static ApiService getInstance(Context context) throws NoInternetException {

        if (NetworkHelper.isNetworkAvaialble(context)) {
            return getApiService();
        } else {
            throw new NoInternetException("No Internet connection available");
        }
    }


    public static class NoInternetException extends Exception {
        public NoInternetException(String message) {
            super(message);
        }
    }

    public static ApiService getApiService() {
        if (sInstance == null) {
            synchronized (ApiAdapter.class) {
                if (sInstance == null) {
                        sInstance = new Retrofit.Builder()
                                .baseUrl(Const.BASE_URL)
                                .client(getOkHttpClient()).addConverterFactory(GsonConverterFactory.create()).build()
                                .create(ApiService.class);

                }
            }
        }
        return sInstance;
    }

    private static OkHttpClient getOkHttpClient() {

        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .retryOnConnectionFailure(true);


        Interceptor interceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();

                // Request customization: add request headers
                Request.Builder requestBuilder = original.newBuilder()
                        .header(Const.APP_KEY, Const.APP_KEY_VALUE);

                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        };

        builder.addInterceptor(interceptor);



        if (BuildConfig.DEBUG) {


            //Print Log
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(httpLoggingInterceptor);



        }
        return builder.readTimeout(30000, TimeUnit.SECONDS)
                .connectTimeout(30000, TimeUnit.SECONDS)
                .build();

    }

}
