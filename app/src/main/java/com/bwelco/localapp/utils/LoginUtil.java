package com.bwelco.localapp.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.bwelco.localapp.http.NormalResponse;
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


    public static void sendLogin(String user, String pass, final LoginCallBack loginCallBack) {
        HttpUtil.getRetrofitInstance().create(LoginService.class)
                .sendLogin(user, pass)
                .enqueue(new Callback<NormalResponse>() {
                    @Override
                    public void onResponse(Call<NormalResponse> call, Response<NormalResponse> response) {
                        if (response != null && response.body() != null &&
                                response.body().isSuccess) {
                            loginCallBack.loginSuccess();
                        } else {
                            loginCallBack.loginFail();
                        }
                    }

                    @Override
                    public void onFailure(Call<NormalResponse> call, Throwable t) {
                        loginCallBack.loginFail();
                        t.printStackTrace();
                    }
                });
    }

    public interface LoginCallBack{
        void loginSuccess();

        void loginFail();
    }

}
