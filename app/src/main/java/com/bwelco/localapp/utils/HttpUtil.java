package com.bwelco.localapp.utils;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by bwelco on 2017/5/17.
 */

public class HttpUtil {

    private static String baseUrl = "http://192.168.10.189:8888";
    private static Retrofit retrofit;

    static {
        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(generateClient())
                .build();
    }


    public static OkHttpClient generateClient() {
        OkHttpClient okHttpClient = new OkHttpClient()
                .newBuilder().retryOnConnectionFailure(true).build();
        return okHttpClient;
    }



    public static Retrofit getRetrofitInstance(){
        return retrofit;
    }
}
