interface WeatherRepository {
    suspend fun getCurrentWeather(): WeatherData
    suspend fun getWeatherForecast(): List<WeatherData>
}