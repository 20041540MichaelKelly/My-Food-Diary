package org.wit.myfooddiary.api

import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object FoodClient {

   // val serviceURL = "https://food-nutrition-information.p.rapidapi.com"
//    val serviceURL = "https://calorieninjas.p.rapidapi.com"
//    val serviceURL = "https://donationweb-hdip-server.herokuapp.com"
    val serviceURL = "https://api.spoonacular.com/"
    fun getApi() : FoodService {
        val gson = GsonBuilder().create()

//        val okHttpClient = OkHttpClient.Builder()
//            .connectTimeout(30, TimeUnit.SECONDS)
//            .writeTimeout(30, TimeUnit.SECONDS)
//            .readTimeout(30, TimeUnit.SECONDS)
////            .addHeader("x-rapidapi-host", "food-nutrition-information.p.rapidapi.com")
////            .addHeader("x-rapidapi-key", "97b2457a9bmsh14c79543dc953b4p1c675ajsnf4fbbb925e6c")
//            .build()

//        val okHttpClient = OkHttpClient.Builder().apply {
//            addInterceptor(
//                Interceptor { chain ->
//                    val builder = chain.request().newBuilder()
//                    builder.header("x-rapidapi-host", "food-nutrition-information.p.rapidapi.com")
//                    builder.header("x-rapidapi-key", "97b2457a9bmsh14c79543dc953b4p1c675ajsnf4fbbb925e6c")
//                    return@Interceptor chain.proceed(builder.build())
//                }
//            )
//        }.connectTimeout(30, TimeUnit.SECONDS)
//            .writeTimeout(30, TimeUnit.SECONDS)
//            .readTimeout(30, TimeUnit.SECONDS)
//            .build()

//        val okHttpClient = OkHttpClient.Builder() {
//            val okHttpBuilder = super.getOkHttpClientBuilder()
//            okHttpBuilder.addInterceptor { chain ->
//                val request = chain.request().newBuilder()
//                val originalHttpUrl = chain.request().url
//                val url =
//                    originalHttpUrl.newBuilder().addQueryParameter("api_key", "your api key value")
//                        .build()
//                request.url(url)
//                val response = chain.proceed(request.build())
//                return@addInterceptor response
//            }
//        }

        val okHttpClient = OkHttpClient.Builder().apply {
            addInterceptor(
                Interceptor { chain ->
                    val builder = chain.request().newBuilder()
                    val originalHttpUrl = chain.request().url()
                    val url = originalHttpUrl.newBuilder().addQueryParameter("apiKey", "xxxxxxxxxxxxxxx").build()
                    builder.url(url)
                    return@Interceptor chain.proceed(builder.build())
                }
            )
        }.connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()

        val apiInterface = Retrofit.Builder()
            .baseUrl(serviceURL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()
        return apiInterface.create(FoodService::class.java)
    }
}