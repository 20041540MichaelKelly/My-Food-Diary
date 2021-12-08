package org.wit.myfooddiary.api

import org.wit.myfooddiary.models.FoodModel
import retrofit2.Call
import retrofit2.http.*

interface FoodService {
    @GET("/fooditems")
    fun getall(): Call<List<FoodModel>>

    @GET("/fooditems/{id}")
    fun get(@Path("id") id: String): Call<FoodModel>

    @DELETE("/fooditems/{id}")
    fun delete(@Path("id") id: String): Call<FoodWraper>

    @POST("/fooditems")
    fun post(@Body donation: FoodModel): Call<FoodWraper>

    @PUT("/fooditems/{id}")
    fun put(@Path("id") id: String,
            @Body foodItem: FoodModel
    ): Call<FoodWraper>
}
