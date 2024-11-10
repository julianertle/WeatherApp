package com.example.jetpackcompose.domain

import WeatherData
import WeatherRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WeatherViewModel(private val repository: WeatherRepository) : ViewModel() {

    private val _currentWeather = MutableStateFlow<WeatherData?>(null)
    val currentWeather: StateFlow<WeatherData?> get() = _currentWeather

    private val _forecast = MutableStateFlow<List<WeatherData>>(emptyList())
    val forecast: StateFlow<List<WeatherData>> get() = _forecast

    init {
        fetchWeatherData()
    }

    private fun fetchWeatherData() {
        viewModelScope.launch {
            _currentWeather.value = repository.getCurrentWeather()
            _forecast.value = repository.getWeatherForecast()
        }
    }
}
