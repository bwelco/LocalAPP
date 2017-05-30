package com.bwelco.localapp.utils;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by bwelco on 2017/5/17.
 */

public class HttpUtil {

    private static String baseUrl;
    private static Retrofit retrofit;

    public static void setBaseUrl(String url) {
        baseUrl = url;
    }


    private static OkHttpClient generateClient() {
        OkHttpClient okHttpClient = new OkHttpClient()
                .newBuilder().retryOnConnectionFailure(true).build();
        return okHttpClient;
    }



    public static Retrofit getRetrofitInstance(){
        if (retrofit == null) {
            synchronized (HttpUtil.class) {
                if (retrofit == null) {
                    retrofit = new Retrofit.Builder()
                            .baseUrl("http://" + baseUrl + ":8888")
                            .addConverterFactory(GsonConverterFactory.create())
                            .client(generateClient())
                            .build();
                }
            }
        }

        return retrofit;
    }
}
