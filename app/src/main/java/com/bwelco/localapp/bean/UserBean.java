package com.bwelco.localapp.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by bwelco on 2017/5/18.
 */

public class UserBean {
    @SerializedName("username")
    public String username;
    @SerializedName("password")
    public String passwd;
    @SerializedName("haspassed")
    public boolean hasPassed;
    @SerializedName("applyreason")
    public String applyReason;
    @SerializedName("isAdmin")
    public boolean isAdmin;
}
