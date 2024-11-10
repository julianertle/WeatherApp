import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.jetpackcompose.data.model.WeatherData
import com.example.jetpackcompose.domain.WeatherViewModel

@Composable
fun WeatherApp(viewModel: WeatherViewModel) {
    val currentWeather = viewModel.currentWeather.collectAsState().value
    val forecast = viewModel.forecast.collectAsState().value

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        currentWeather?.let {
            CurrentWeatherView(weather = it)
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Forecast", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(8.dp))
        LazyColumn {
            items(forecast) { weather ->
                ForecastWeatherView(weather)
            }
        }
    }
}

@Composable
fun CurrentWeatherView(weather: WeatherData) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            painter = rememberAsyncImagePainter(weather.iconUrl),
            contentDescription = null,
            modifier = Modifier.size(80.dp),
            contentScale = ContentScale.Crop
        )
        Text(text = "${weather.temperature}°C", style = MaterialTheme.typography.displayLarge)
        Text(text = weather.condition, style = MaterialTheme.typography.bodyLarge, color = Color.Gray)
    }
}

@Composable
fun ForecastWeatherView(weather: WeatherData) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = rememberAsyncImagePainter(weather.iconUrl),
            contentDescription = null,
            modifier = Modifier.size(40.dp),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(text = weather.date, style = MaterialTheme.typography.bodyLarge)
            Text(text = "${weather.temperature}°C - ${weather.condition}", color = Color.Gray)
        }
    }
}
