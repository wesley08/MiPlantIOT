package com.example.miplantiot.api;

/**
 * Created by Robby Dianputra on 10/31/2017.
 */


import com.example.miplantiot.Model.city.ItemCity;
import com.example.miplantiot.Model.cost.ItemCost;
import com.example.miplantiot.Model.province.ItemProvince;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {

     //Province
    @GET("province")
    @Headers("key:912cc0ec0d69b9b24d1b47173598e16c")
    Call<ItemProvince> getProvince();

    // City
    @GET("city")
    @Headers("key:912cc0ec0d69b9b24d1b47173598e16c")
    Call<ItemCity> getCity (@Query("province") String province);

    // Cost
    @FormUrlEncoded
    @POST("cost")
    Call<ItemCost> getCost (@Field("key") String Token,
                            @Field("origin") String origin,
                            @Field("destination") String destination,
                            @Field("weight") String weight,
                            @Field("courier") String courier);

}
