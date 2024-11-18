import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

// Imports for TextField and SearchBar specific components
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.platform.LocalFocusManager
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.jetpackcompose.model.WeatherViewModel
import kotlin.math.exp

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun SearchBarSample(weatherViewModel: WeatherViewModel = viewModel(),selectedMenu: String = "") {
    val textFieldState = rememberTextFieldState()
    var expanded by rememberSaveable { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current

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
                modifier = Modifier
                    .fillMaxWidth(),
                inputField = {
                    SearchBarDefaults.InputField(
                        state = textFieldState,
                        onSearch = { query ->
                            println("Search input: $query")


                            if(query.isNotEmpty()){
                                if(selectedMenu == "Home"){
                                    weatherViewModel.fetchWeatherData(query)
                                    recentSearches = (listOf(query) + recentSearches).distinct().take(5)
                                }
                                else if (selectedMenu == "Forecast"){
                                    weatherViewModel.fetchForecastData(query)
                                    recentSearches = (listOf(query) + recentSearches).distinct().take(5)
                                }

                            }
                            focusManager.clearFocus()
                            textFieldState.clearText()
                            expanded = false
                        },
                        expanded = expanded,
                        onExpandedChange = {
                            // Only allow expansion if recentSearches is not empty
                            expanded = it && recentSearches.isNotEmpty()
                        },
                        placeholder = { Text("Search any city") },
                        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                        trailingIcon = { Icon(Icons.Default.MoreVert, contentDescription = null) }
                    )
                },
                expanded = expanded,
                onExpandedChange = {
                    // Only allow expansion if recentSearches is not empty
                    expanded = it && recentSearches.isNotEmpty()
                },
            ) {
                if (recentSearches.isNotEmpty() && expanded) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(max = 300.dp)
                            .padding(8.dp) // Optional: Add padding to the dropdown
                    ) {
                        // Display recent searches
                        items(recentSearches.size) { idx ->
                            val resultText = recentSearches[idx]
                            ListItem(
                                headlineContent = { Text(resultText) },
                                leadingContent = { Icon(Icons.Filled.Star, contentDescription = null) },
                                colors = ListItemDefaults.colors(containerColor = Color.Transparent),
                                modifier = Modifier
                                    .clickable {
                                        // Set the text field with the selected search and activate `onSearch`
                                        textFieldState.setTextAndPlaceCursorAtEnd(resultText)
                                        expanded = false
                                        weatherViewModel.fetchWeatherData(resultText)

                                        // Update recent searches
                                        recentSearches = (listOf(resultText) + recentSearches).distinct().take(5)

                                        // Clear focus from the input field
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
