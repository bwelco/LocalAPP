package com.bwelco.localapp.http;

import com.bwelco.localapp.bean.UserBean;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by bwelco on 2017/5/18.
 */

public interface UserManagerService {

    @GET("getUserList")
    Call<List<UserBean>> getUserList();

    @GET("updateUserToAdmin")
    Call<NormalResponse> updateUserToAdmin(@Query("userName") String userName);

    @GET("agreeApply")
    Call<NormalResponse> agreeUserApply(@Query("userName") String userName);

    @GET("deleteUser")
    Call<NormalResponse> deleteUser(@Query("userName") String userName);
}
