import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpackcompose.api.WeatherApiService
import com.example.jetpackcompose.data.WeatherData
import com.example.jetpackcompose.data.WeatherRepository
import kotlinx.coroutines.launch
import androidx.compose.runtime.State // Importing the State class from Compose
import coil.compose.rememberImagePainter // For loading images into Compose Image view
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class WeatherViewModel(private val weatherRepository: WeatherRepository) : ViewModel() {

    private val _currentWeather = MutableStateFlow<WeatherData?>(null)
    val currentWeather: StateFlow<WeatherData?> = _currentWeather

    private val _forecast = MutableStateFlow<List<WeatherData>>(emptyList())
    val forecast: StateFlow<List<WeatherData>> = _forecast

    private val _iconUrl = MutableStateFlow<String?>(null) // For weather icon
    val iconUrl: StateFlow<String?> get() = _iconUrl

    // Fetch weather data
    fun fetchWeatherData(city: String) {
        viewModelScope.launch {
            val weatherData = WeatherApiService.fetchWeather(city)
            if (weatherData != null) {
                _currentWeather.value = weatherData
                val iconId = weatherData.weather.firstOrNull()?.icon ?: ""
                fetchWeatherIcon(iconId)
            }
        }
    }

    // Fetch weather icon based on the iconId
    private fun fetchWeatherIcon(iconId: String) {
        if (iconId.isNotEmpty()) {
            val iconUrl = "https://openweathermap.org/img/wn/$iconId@2x.png"
            _iconUrl.value = iconUrl
        }
    }
}


