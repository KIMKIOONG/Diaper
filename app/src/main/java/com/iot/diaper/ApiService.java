package com.iot.diaper;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by test2 on 2017-07-07.
 */

public interface ApiService {
    @FormUrlEncoded
    @POST("baby")
    Call<ResponseBody>postData(@Field("babyid") String id,
                               @Field("password") String pw,
                               @Field("Name") String name);

    @FormUrlEncoded
    @POST("/device")
    Call<ResponseBody>postDataToDevice(@Field("babyid") String id,
                                       @Field("deviceid") String deviceId);//연동번호 입력

    @GET("/status/{babyid}")
    Call<ResponseBody>postDataForEventUpdate(@Path("babyid") String id);

    @GET("baby")
    Call<ResponseBody>getData();

    @GET("/count/{babyid}")
    Call<ResponseBody>getcountData(@Path("babyid") String id);

    @GET("/baby/{babyid}&{password}")
    Call<ResponseBody>checkDataToLogin(@Path("babyid") String id,
                                       @Path("password") String pw);//로그인할때 확인하는거
}