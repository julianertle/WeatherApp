package com.example.jetpackcompose.api

import android.util.Log
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
    private const val API_KEY = "31c02750cfcd532431987a20153cde7c"

    private val client = OkHttpClient.Builder().build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create()) // Add Gson converter for JSON parsing
        .build()

    private val api = retrofit.create(WeatherApi::class.java)

    interface WeatherApi {
        @GET("weather")
        suspend fun fetchWeather(
            @Query("q") city: String,
            @Query("appid") apiKey: String = API_KEY,
            @Query("units") units: String = "metric" // Add the units parameter with a default value
        ): retrofit2.Response<WeatherData>
    }

    suspend fun fetchWeather(city: String): WeatherData? {
        return try {
            // Making the API call on the IO dispatcher
            withContext(Dispatchers.IO) {
                val response = api.fetchWeather(city)

                // Check if the response is successful and return the body (WeatherData)
                if (response.isSuccessful) {
                    response.body() // Automatically parsed into WeatherData
                } else {
                    // If the response is not successful, log the error
                    Log.e("WeatherApiService", "Failed to fetch weather for $city: ${response.code()}")
                    null
                }
            }
        } catch (e: Exception) {
            // Catch any exceptions and log the error
            Log.e("WeatherApiService", "Error fetching weather data for $city: ${e.message}")
            null
        }
    }


}
