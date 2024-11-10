package com.example.jetpackcompose.data.model

data class WeatherData(
    val temperature: Double,
    val condition: String,
    val iconUrl: String,
    val date: String
)
