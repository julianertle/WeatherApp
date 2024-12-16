package com.example.jetpackcompose.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpackcompose.api.WeatherApiService
import com.example.jetpackcompose.data.ForecastItem
import com.example.jetpackcompose.data.WeatherData
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class WeatherViewModel : ViewModel() {

    private val _currentWeather = MutableStateFlow<WeatherData?>(null)
    val currentWeather: StateFlow<WeatherData?> = _currentWeather

    private val _forecast = MutableStateFlow<List<ForecastItem>>(emptyList())
    val forecast: StateFlow<List<ForecastItem>> = _forecast

    private val _iconUrl = MutableStateFlow<String?>(null)
    val iconUrl: StateFlow<String?> get() = _iconUrl

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> get() = _errorMessage

    fun fetchWeatherData(city: String, apiKey: String) {
        viewModelScope.launch {
            try {
                val weatherData = WeatherApiService.fetchWeather(city, apiKey)
                if (weatherData != null) {
                    _currentWeather.value = weatherData
                    val iconId = weatherData.weather.firstOrNull()?.icon ?: ""
                    fetchWeatherIcon(iconId)
                    _errorMessage.value = null // Clear any previous errors
                } else {
                    // If weather data is null, set an error message
                    _errorMessage.value = "Failed to fetch weather. Please check your API key or city name."
                }
            } catch (e: Exception) {
                // If there is an error (e.g., invalid API key), show the error message
                _errorMessage.value = "An error occurred: ${e.localizedMessage}"
            }
        }
    }

    private fun fetchWeatherIcon(iconId: String) {
        if (iconId.isNotEmpty()) {
            val iconUrl = "https://openweathermap.org/img/wn/$iconId@2x.png"
            _iconUrl.value = iconUrl
        }
    }

    fun fetchForecastData(city: String, apiKey: String) {
        viewModelScope.launch {
            try {
                val forecastResponse = WeatherApiService.fetchForecast(city, apiKey)
                if (forecastResponse != null) {
                    _forecast.value = forecastResponse.list
                    _errorMessage.value = null // Clear any previous errors
                } else {
                    _errorMessage.value = "Failed to fetch forecast. Please check your API key or city name."
                }
            } catch (e: Exception) {
                _errorMessage.value = "An error occurred: ${e.localizedMessage}"
            }
        }
    }
}
