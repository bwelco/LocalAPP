package com.bwelco.localapp.http;

import com.google.gson.annotations.SerializedName;

/**
 * Created by bwelco on 2017/5/17.
 */

public class NormalResponse {

    @SerializedName("success")
    public boolean isSuccess;

    @SerializedName("reason")
    public String reason;
}
