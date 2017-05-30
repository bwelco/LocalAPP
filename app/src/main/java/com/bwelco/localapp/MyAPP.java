package com.bwelco.localapp;

import android.app.Application;
import android.content.Context;

import com.bwelco.localapp.utils.HttpUtil;
import com.bwelco.localapp.utils.IPConfigUtil;
import com.bwelco.localapp.utils.ToastUtil;

/**
 * Created by bwelco on 2017/5/17.
 */

public class MyAPP extends Application {


    public static Context application;

    @Override
    public void onCreate() {
        super.onCreate();
        ToastUtil.ToastMgr.builder.init(getApplicationContext());
        application = getApplicationContext();
        HttpUtil.setBaseUrl(IPConfigUtil.getIpconfig(getApplicationContext()));
    }
}
