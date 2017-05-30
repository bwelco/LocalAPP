package com.bwelco.localapp.utils;

import android.app.Activity;
import android.content.Context;

/**
 * Created by bwelco on 2017/5/23.
 */

public class IPConfigUtil {

    public static final String IPCONFIG = "ip_config";
    public static final String IP_CONFIG_TABLE_KEY = "ip_config_key";
    public static final String DEFAULT_IP = "192.168.10.206";

    public static void setIpconfig(Context context, String ipconfig) {
        context.getSharedPreferences(IPCONFIG, Activity.MODE_PRIVATE)
                .edit().putString(IP_CONFIG_TABLE_KEY, ipconfig)
                .apply();
    }

    public static String getIpconfig(Context context) {
        return context.getSharedPreferences(IPCONFIG, Activity.MODE_PRIVATE)
                .getString(IP_CONFIG_TABLE_KEY, DEFAULT_IP);
    }
}
