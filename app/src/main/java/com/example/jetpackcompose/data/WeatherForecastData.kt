package com.example.jetpackcompose.data
typealias WeatherForecastData = ForecastResponse

data class ForecastResponse(
    val cod: String,
    val message: Int,
    val cnt: Int,
    val list: List<ForecastItem>
)

data class ForecastItem(
    val dt: Long,
    val main: Main,
    val weather: List<Weather>,
    val clouds: Clouds,
    val wind: Wind,
    val visibility: Int,
    val pop: Double,
    val sys: ForecastSys,
    val dt_txt: String,
    val rain: Rain? = null // Rain may not always be present
)

data class ForecastSys(val pod: String)
data class Rain(val `3h`: Double? = null)