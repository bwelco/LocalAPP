package com.bwelco.localapp.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.bwelco.localapp.http.LoginResponse;
import com.bwelco.localapp.http.LoginService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by bwelco on 2017/5/17.
 */

public class LoginUtil {

    private static final String USER_PREF = "user_pref";
    private static final String USERNAME_KEY = "username";
    private static final String PASSWD_KEY = "passwd";

    public static void saveUser(Context context, String user, String pass) {
        SharedPreferences sharedPreferences =
                context.getSharedPreferences(USER_PREF, Activity.MODE_PRIVATE);
        sharedPreferences.edit().
                putString(USERNAME_KEY, user).putString(PASSWD_KEY, pass).apply();
    }

    public static String getUserName(Context context) {
        return context.getSharedPreferences(USER_PREF, Activity.MODE_PRIVATE)
                .getString(USERNAME_KEY, "");
    }

    public static String getPass(Context context) {
        return context.getSharedPreferences(USER_PREF, Activity.MODE_PRIVATE)
                .getString(PASSWD_KEY, "");
    }


    public static void sendLogin(String user, String pass, LoginCallBack loginCallBack){
        HttpUtil.getRetrofitInstance().create(LoginService.class)
                .sendLogin(user, pass)
                .enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        Log.i("admin", "response : " + response.body().success);
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        Log.i("admin", "fail");
                        t.printStackTrace();
                    }
                });
    }

    public interface LoginCallBack{
        void loginCallback(boolean isSucess);
    }

}
