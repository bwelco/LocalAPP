package com.bwelco.localapp.bean;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

/**
 * Created by bwelco on 2017/5/19.
 */

public class DoorEventBean implements Comparable<DoorEventBean> {
    @SerializedName("userName")
    public String userName;

    @SerializedName("time")
    public long time;

    @SerializedName("openType")
    public String openType;

    @Override
    public int compareTo(@NonNull DoorEventBean o) {
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
