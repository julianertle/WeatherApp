import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.input.clearText
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.text.input.setTextAndPlaceCursorAtEnd
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp

// Imports for TextField and SearchBar specific components
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.platform.LocalFocusManager
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.jetpackcompose.model.WeatherViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBarSample(
    weatherViewModel: WeatherViewModel = viewModel(),
    selectedMenu: String = "",
    apiKey: String,  // Add apiKey as a parameter
    onQueryChanged: (String) -> Unit
) {
    val textFieldState = rememberTextFieldState()
    var expanded by rememberSaveable { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current
    var cityName by rememberSaveable { mutableStateOf("") }

    // A list to store recent searches
    var recentSearches by rememberSaveable { mutableStateOf(listOf<String>()) }

    // Collect the current weather data
    val currentWeather by weatherViewModel.currentWeather.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(max = 200.dp)
            .semantics { isTraversalGroup = true }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
        ) {
            // Search Bar
            SearchBar(
                modifier = Modifier.fillMaxWidth(),
                inputField = {
                    SearchBarDefaults.InputField(
                        state = textFieldState,
                        onSearch = { inputQuery ->
                            cityName = inputQuery
                            onQueryChanged(cityName)  // Pass the query back to the parent composable
                            println("Search input: $inputQuery")

                            // Perform actions based on selectedMenu
                            if (inputQuery.isNotEmpty()) {
                                when (selectedMenu) {
                                    "Home" -> weatherViewModel.fetchWeatherData(inputQuery, apiKey)
                                    "Forecast" -> weatherViewModel.fetchForecastData(inputQuery, apiKey)
                                }
                                // Update recent searches
                                recentSearches = (listOf(inputQuery) + recentSearches).distinct().take(5)
                            }

                            // Clear the text field and close the keyboard
                            focusManager.clearFocus()
                            textFieldState.clearText()
                            expanded = false
                        },
                        expanded = expanded,
                        onExpandedChange = {
                            expanded = it && recentSearches.isNotEmpty()
                        },
                        placeholder = { Text("Search any city") },
                        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                        trailingIcon = { Icon(Icons.Default.MoreVert, contentDescription = null) }
                    )
                },
                expanded = expanded,
                onExpandedChange = {
                    expanded = it && recentSearches.isNotEmpty()
                },
            ) {
                // Display recent searches if expanded
                if (recentSearches.isNotEmpty() && expanded) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(max = 300.dp)
                            .padding(8.dp)
                    ) {
                        items(recentSearches.size) { idx ->
                            val resultText = recentSearches[idx]
                            ListItem(
                                headlineContent = { Text(resultText) },
                                leadingContent = { Icon(Icons.Filled.Star, contentDescription = null) },
                                colors = ListItemDefaults.colors(containerColor = Color.Transparent),
                                modifier = Modifier
                                    .clickable {
                                        // Set the text and cursor, fetch weather, and close the suggestions
                                        textFieldState.setTextAndPlaceCursorAtEnd(resultText)
                                        expanded = false
                                        weatherViewModel.fetchWeatherData(resultText, apiKey)

                                        // Update recent searches
                                        recentSearches = (listOf(resultText) + recentSearches).distinct().take(5)
                                        focusManager.clearFocus()
                                    }
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp, vertical = 4.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}
