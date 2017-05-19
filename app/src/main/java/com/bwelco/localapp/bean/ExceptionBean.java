package com.bwelco.localapp.bean;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

/**
 * Created by bwelco on 2017/5/19.
 */

public class ExceptionBean implements Comparable<ExceptionBean> {
    @SerializedName("errType")
    public String errType;

    @SerializedName("userName")
    public String userName;

    @SerializedName("time")
    public long time;

    @Override
    public int compareTo(@NonNull ExceptionBean o) {
        if (o.time < time) {
            return -1;
        } else if (o.time == time) {
            return 0;
        } else if (o.time > time) {
            return 1;
        }

        return 0;
    }
}
