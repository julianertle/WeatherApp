package com.example.jetpackcompose.domain

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpackcompose.data.WeatherData
import com.example.jetpackcompose.data.WeatherRepository

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WeatherViewModel(private val weatherRepository: WeatherRepository) : ViewModel() {

    // StateFlow to hold the current weather data
    private val _currentWeather = MutableStateFlow<WeatherData?>(null)
    val currentWeather: StateFlow<WeatherData?> = _currentWeather

    // StateFlow to hold the forecast data (a list of WeatherData)
    private val _forecast = MutableStateFlow<List<WeatherData>>(emptyList())
    val forecast: StateFlow<List<WeatherData>> = _forecast

    // Load current weather and forecast
    init {
        loadWeatherData()
    }

    // Function to load the current weather and forecast
    private fun loadWeatherData() {
        // Load current weather
        viewModelScope.launch {
            try {
                val current = weatherRepository.getCurrentWeather()
                _currentWeather.value = current
            } catch (e: Exception) {
                // Handle error appropriately, e.g., logging or showing an error message
                _currentWeather.value = null
            }
        }

        // Load forecast
        viewModelScope.launch {
            try {
                val weatherForecast = weatherRepository.getWeatherForecast()
                _forecast.value = weatherForecast
            } catch (e: Exception) {
                // Handle error appropriately
                _forecast.value = emptyList()
            }
        }
    }
}
