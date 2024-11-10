import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
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

    var selectedItem by remember { mutableStateOf(0) } // 0: Home, 1: Forecast, 2: Settings

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Display current weather and forecast
        if (selectedItem == 0) {
            currentWeather?.let {
                CurrentWeatherView(weather = it)
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        if (selectedItem == 1) {
            Text(text = "Forecast", style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(8.dp))
            LazyColumn {
                items(forecast) { weather ->
                    ForecastWeatherView(weather)
                }
            }
        }

        // Add the Bottom Navigation Bar
        Spacer(modifier = Modifier.weight(1f)) // Push the BottomNavigation to the bottom
        BottomNavBar(
            selectedItem = selectedItem,
            onItemSelected = { selectedItem = it }
        )
    }
}

@Composable
fun BottomNavBar(selectedItem: Int, onItemSelected: (Int) -> Unit) {
    BottomNavigation(
        modifier = Modifier.fillMaxWidth(),
        backgroundColor = MaterialTheme.colorScheme.primary
    ) {
        BottomNavigationItem(
            selected = selectedItem == 0,
            onClick = { onItemSelected(0) },
            label = { Text("Home") },
            icon = { Icon(imageVector = Icons.Filled.Home, contentDescription = "Home") }
        )
        BottomNavigationItem(
            selected = selectedItem == 1,
            onClick = { onItemSelected(1) },
            label = { Text("Forecast") },
            icon = { Icon(imageVector = Icons.Filled.Schedule, contentDescription = "Forecast") }
        )
        BottomNavigationItem(
            selected = selectedItem == 2,
            onClick = { onItemSelected(2) },
            label = { Text("Settings") },
            icon = { Icon(imageVector = Icons.Filled.Settings, contentDescription = "Settings") }
        )
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
