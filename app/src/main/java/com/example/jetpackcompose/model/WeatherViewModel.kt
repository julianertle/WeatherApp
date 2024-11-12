package com.example.jetpackcompose.domain

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpackcompose.api.WeatherApiService
import com.example.jetpackcompose.data.WeatherData
import com.example.jetpackcompose.data.WeatherRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import androidx.compose.runtime.State // Importing the State class from Compose

class WeatherViewModel(private val weatherRepository: WeatherRepository) : ViewModel() {

    // Update this to hold WeatherData instead of String
    private val _weatherData = mutableStateOf<WeatherData?>(null)
    val weatherData: State<WeatherData?> get() = _weatherData

    private val _currentWeather = MutableStateFlow<WeatherData?>(null)
    val currentWeather: StateFlow<WeatherData?> = _currentWeather

    private val _forecast = MutableStateFlow<List<WeatherData>>(emptyList())
    val forecast: StateFlow<List<WeatherData>> = _forecast

    fun fetchWeatherData(city: String) {
        viewModelScope.launch {
            val weatherData = WeatherApiService.fetchWeather(city)
            if (weatherData != null) {
                _weatherData.value = weatherData // Now it's assigning a WeatherData object
                Log.d("WeatherViewModel", "Fetched weather data for $city: ${weatherData.name}, ${weatherData.main.temp}")
            } else {
                Log.e("WeatherViewModel", "Failed to fetch weather data.")
            }
        }
    }
}
