package org.wit.myfooddiary.api

import org.wit.myfooddiary.models.FoodModel
import retrofit2.Call
import retrofit2.http.*
import retrofit2.http.GET




interface FoodService {

    @GET("recipes/findByNutrients?maxCalories=800&number=10")
    fun getall(): Call<List<FoodModel>>

    @GET("recipes/findByNutrients")
    fun getallbydescription(@Query(value = "maxCalories") maxCals: String?, @Query(value = "number") numberOfItems: String? ): Call<List<FoodModel>>?

    @POST("/recipes/Nutrients")
    fun create(@Body foodItem: FoodModel): Call<FoodWraper>

}
