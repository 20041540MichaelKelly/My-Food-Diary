package org.wit.myfooddiary.api

import org.wit.myfooddiary.models.FoodModel
import retrofit2.Call
import retrofit2.http.*
import retrofit2.http.GET




interface FoodService {

    @GET("recipes/findByNutrients?maxCalories=800&number=100")
    fun getall(): Call<List<FoodModel>>

    @GET("items/list/filter/{filter}")
    fun getItems(@Path(value = "filter") filter: String?): Call<FoodModel?>?

    @GET("/food/{id}")
    fun get(@Path("id") id: String): Call<FoodModel>

    @DELETE("/food/{id}")
    fun delete(@Path("id") id: String): Call<FoodWraper>

    @POST("/food")
    fun post(@Body donation: FoodModel): Call<FoodWraper>

    @PUT("/food/{id}")
    fun put(@Path("id") id: String,
            @Body foodItem: FoodModel
    ): Call<FoodWraper>

}
