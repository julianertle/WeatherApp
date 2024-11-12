package com.example.jetpackcompose.api

import com.example.jetpackcompose.data.WeatherData
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("weather")
    suspend fun fetchRawWeather(
        @Query("q") city: String,
        @Query("appid") apiKey: String = "31c02750cfcd532431987a20153cde7c" // Add your API key here
    ): Response<String>
}
