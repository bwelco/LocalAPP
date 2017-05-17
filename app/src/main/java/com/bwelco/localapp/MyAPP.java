package com.bwelco.localapp;

import android.app.Application;

import com.bwelco.localapp.utils.ToastUtil;

/**
 * Created by bwelco on 2017/5/17.
 */

public class MyAPP extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        ToastUtil.ToastMgr.builder.init(getApplicationContext());
    }
}
