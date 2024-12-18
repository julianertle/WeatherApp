package com.example.jetpackcompose.api

import android.util.Log
import com.example.jetpackcompose.data.ForecastResponse
import com.example.jetpackcompose.data.WeatherData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

object WeatherApiService {
    private const val BASE_URL = "https://api.openweathermap.org/data/2.5/"

    private val client = OkHttpClient.Builder().build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val api = retrofit.create(WeatherApi::class.java)

    interface WeatherApi {
        @GET("weather")
        suspend fun fetchWeather(
            @Query("q") city: String,
            @Query("appid") apiKey: String,
            @Query("units") units: String = "metric"
        ): retrofit2.Response<WeatherData>

        @GET("forecast")
        suspend fun fetchForecast(
            @Query("q") city: String,
            @Query("appid") apiKey: String,
            @Query("units") units: String = "metric"
        ): retrofit2.Response<ForecastResponse>
    }

    private suspend fun <T> fetchApiData(
        fetchFunction: suspend () -> retrofit2.Response<T>
    ): T? {
        return try {
            withContext(Dispatchers.IO) {
                val response = fetchFunction()
                if (response.isSuccessful) {
                    response.body()
                } else {
                    Log.e("WeatherApiService", "Failed to fetch data: ${response.code()}")
                    null
                }
            }
        } catch (e: Exception) {
            Log.e("WeatherApiService", "Error fetching data: ${e.message}")
            null
        }
    }

    suspend fun fetchWeather(city: String, apiKey: String): WeatherData? {
        return fetchApiData { api.fetchWeather(city, apiKey) }
    }

    suspend fun fetchForecast(city: String, apiKey: String): ForecastResponse? {
        return fetchApiData { api.fetchForecast(city, apiKey) }
    }
}
