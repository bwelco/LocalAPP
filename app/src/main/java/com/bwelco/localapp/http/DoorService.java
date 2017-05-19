package com.bwelco.localapp.http;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by bwelco on 2017/5/19.
 */

public interface DoorService {

    @GET("openDoor")
    Call<NormalResponse> openDoor(@Query("userName") String userName);

    @GET("closeDoor")
    Call<NormalResponse> closrDoor(@Query("userName") String userName);
}
