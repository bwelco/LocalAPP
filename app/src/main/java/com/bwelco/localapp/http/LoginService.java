package com.bwelco.localapp.http;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by bwelco on 2017/5/17.
 */

public interface LoginService {

    @GET("login")
    Call<LoginResponse>
               sendLogin(@Query("user") String user, @Query("pass") String pass);

    @GET("register")
    Call<NormalResponse>
    sendRegister(@Query("user") String user, @Query("pass") String passwd,
                 @Query("apply_info") String applyInfo);
}
