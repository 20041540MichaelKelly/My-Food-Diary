package org.wit.myfooddiary.api

import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.wit.myfooddiary.BuildConfig
import org.wit.myfooddiary.R
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object FoodClient {
    val serviceURL = "https://api.spoonacular.com/"
    fun getApi() : FoodService {
        val gson = GsonBuilder().create()
        val API_KEY = BuildConfig.APIKEY



        val okHttpClient = OkHttpClient.Builder().apply {
            addInterceptor(
                Interceptor { chain ->
                    val builder = chain.request().newBuilder()
                    val originalHttpUrl = chain.request().url()
                    val url = originalHttpUrl.newBuilder().addQueryParameter("apiKey", API_KEY).build() //this is so I wont have to keep using it everytime
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