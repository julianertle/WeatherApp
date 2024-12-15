package com.example.jetpackcompose.model
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpackcompose.api.WeatherApiService
import com.example.jetpackcompose.data.ForecastItem
import com.example.jetpackcompose.data.WeatherData
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class WeatherViewModel() : ViewModel() {

    private val _currentWeather = MutableStateFlow<WeatherData?>(null)
    val currentWeather: StateFlow<WeatherData?> = _currentWeather

    private val _forecast = MutableStateFlow<List<ForecastItem>>(emptyList()) // Change type here
    val forecast: StateFlow<List<ForecastItem>> = _forecast

    private val _iconUrl = MutableStateFlow<String?>(null) // For weather icon
    val iconUrl: StateFlow<String?> get() = _iconUrl

    fun fetchWeatherData(city: String,apiKey: String) {
        viewModelScope.launch {
            val weatherData = WeatherApiService.fetchWeather(city,apiKey)
            if (weatherData != null) {
                _currentWeather.value = weatherData
                val iconId = weatherData.weather.firstOrNull()?.icon ?: ""
                fetchWeatherIcon(iconId)
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
            val forecastResponse = WeatherApiService.fetchForecast(city, apiKey)
            if (forecastResponse != null) {
                _forecast.value = forecastResponse.list // Now correctly matches the type
            }
        }
    }
}


