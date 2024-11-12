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

    // Function to load current weather and forecast for a given query
    fun fetchWeatherData(query: String) {
        // Load current weather
        viewModelScope.launch {
            try {
                val current = weatherRepository.getCurrentWeather(query)
                _currentWeather.value = current
            } catch (e: Exception) {
                _currentWeather.value = null
            }
        }

        /*// Load forecast (this is just a placeholder)
        viewModelScope.launch {
            try {
                val weatherForecast = weatherRepository.getWeatherForecast(query)
                _forecast.value = weatherForecast
            } catch (e: Exception) {
                _forecast.value = emptyList()
            }
        }*/
    }
    fun fetchRawWeatherData(query: String) {
        // Load current weather
        viewModelScope.launch {
            try {
                val current = weatherRepository.getCurrentRawWeather(query)
                //_currentWeather.value = current
            } catch (e: Exception) {
                _currentWeather.value = null
            }
        }
    }
}
