package com.example.jetpackcompose.api

import android.util.Log
import com.example.jetpackcompose.data.WeatherData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request

object WeatherApiService {
    // Corrected BASE_URL to end with a slash
    private val BASE_URL = "https://api.openweathermap.org/data/2.5/"  // Ensure the base URL ends with "/"
    private const val API_KEY = "31c02750cfcd532431987a20153cde7c"
    private const val TAG = "WeatherService" // For logging

    private val client = OkHttpClient()

    // Function to fetch raw JSON weather data for a city
    suspend fun fetchRawWeather(city: String): String? {
        // Switch to the IO dispatcher for network operations
        return withContext(Dispatchers.IO) {
            val url = "$BASE_URL/weather?q=$city&appid=$API_KEY"  // Ensure the full URL is correct
            val request = Request.Builder()
                .url(url)
                .build()

            return@withContext try {
                client.newCall(request).execute().use { response ->
                    if (response.isSuccessful) {
                        val rawJson = response.body?.string()
                        Log.d(TAG, "Raw weather data for $city: $rawJson")
                        rawJson
                    } else {
                        Log.e(TAG, "Failed to fetch weather data: ${response.code}")
                        null
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error fetching weather data for $city: ${e.message}")
                null
            }
        }
    }
}
