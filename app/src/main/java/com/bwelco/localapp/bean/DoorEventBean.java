package com.bwelco.localapp.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by bwelco on 2017/5/19.
 */

public class DoorEventBean {
    @SerializedName("userName")
    public String userName;

    @SerializedName("time")
    public long time;

    @SerializedName("openType")
    public String openType;
}
