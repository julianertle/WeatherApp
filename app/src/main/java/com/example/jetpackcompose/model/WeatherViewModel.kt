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

    private val _weatherData = mutableStateOf<String?>(null)
    val weatherData: State<String?> get() = _weatherData
    // StateFlow to hold the current weather data
    private val _currentWeather = MutableStateFlow<WeatherData?>(null)
    val currentWeather: StateFlow<WeatherData?> = _currentWeather

    // StateFlow to hold the forecast data (a list of WeatherData)
    private val _forecast = MutableStateFlow<List<WeatherData>>(emptyList())
    val forecast: StateFlow<List<WeatherData>> = _forecast


    fun fetchRawWeatherData(city: String) {
        viewModelScope.launch {
            val data = WeatherApiService.fetchRawWeather(city) // Corrected reference
            if (data != null) {
                _weatherData.value = data // Update state with fetched data
                Log.d("WeatherViewModel", "Fetched weather data: $data")
            } else {
                Log.e("WeatherViewModel", "Failed to fetch weather data.")
            }
        }
    }
}
