package com.bwelco.localapp.http;

import com.google.gson.annotations.SerializedName;

/**
 * Created by bwelco on 2017/5/19.
 */

public class LoginResponse {
    @SerializedName("username")
    public String userName;

    @SerializedName("passwd")
    public String passwd;

    @SerializedName("isAdmin")
    public boolean isAdmin;

    @SerializedName("hasPassed")
    public boolean hasPassed;

    @SerializedName("isSuccess")
    public boolean isSuccess;

    @SerializedName("reason")
    public String reason;
}
