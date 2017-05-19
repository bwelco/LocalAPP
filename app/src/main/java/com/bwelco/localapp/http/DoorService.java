package com.bwelco.localapp.http;

import com.bwelco.localapp.bean.DoorEventBean;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by bwelco on 2017/5/19.
 */

public interface DoorService {

    String TYPE_NFC = "type_nfc";
    String TYPE_NETWORK = "type_network";

    @GET("openDoor")
    Call<NormalResponse> openDoor(@Query("userName") String userName,
                                  @Query("time") long time, @Query("openType") String openType);

    @GET("closeDoor")
    Call<NormalResponse> closrDoor(@Query("userName") String userName,
                                   @Query("time") long time);

    @GET("getDoorEventList")
    Call<List<DoorEventBean>> getDoorEventList();
}
