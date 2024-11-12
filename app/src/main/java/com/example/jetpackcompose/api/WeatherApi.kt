package com.example.jetpackcompose.api

import com.example.jetpackcompose.data.WeatherData
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("weather")
    suspend fun getWeatherByCity(
        @Query("q") city: String,
        @Query("appid") apiKey: String
    ): Response<WeatherData>


    @GET("weather")
    suspend fun getRawWeatherByCity(
        @Query("q") city: String,
        @Query("appid") apiKey: String
    ): Response<ResponseBody>
}
