package com.bwelco.localapp.http;

import com.bwelco.localapp.bean.ExceptionBean;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by bwelco on 2017/5/19.
 */

public interface ExceptionService {

    @GET("exception/loginFail")
    Call<NormalResponse> sendLoginFailException(
            @Query("userName") String userName, @Query("time") long time);

    @GET("exception/trySpuerAdminFail")
    Call<NormalResponse> sendTrySuperAdminFail(@Query("time") long time);

    @GET("exception/getExceptionList")
    Call<List<ExceptionBean>> getExceptionList();
}
