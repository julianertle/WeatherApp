package com.example.jetpackcompose.ui

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.example.jetpackcompose.data.Keys
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.delay
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown


val Context.dataStore by preferencesDataStore(name = "settings")

@Composable
fun SettingsView(onSave: () -> Unit) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var apiToken by remember { mutableStateOf("") }
    var hometown by remember { mutableStateOf("") }
    var selectedTimerOption by remember { mutableStateOf("Deactivated") } // Default option
    val timerOptions = listOf("Deactivated", "10s", "30s", "60s", "30 min", "60 min")

    // Load the saved values
    LaunchedEffect(Unit) {
        context.dataStore.data.map { preferences ->
            val apiToken = preferences[Keys.API_TOKEN_KEY] ?: ""
            val hometown = preferences[Keys.HOMETOWN_KEY] ?: ""
            val timerOption = preferences[Keys.TIMER_OPTION_KEY] ?: "Deactivated"
            Triple(apiToken, hometown, timerOption)
        }.collect { (loadedApiToken, loadedHometown, loadedTimerOption) ->
            apiToken = loadedApiToken
            hometown = loadedHometown
            selectedTimerOption = loadedTimerOption
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter) // Align content at the top
        ) {
            // Hometown input
            Text("Your hometown:", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = hometown,
                onValueChange = { hometown = it },
                label = { Text("Hometown") },
                textStyle = TextStyle(fontSize = 20.sp),  // Adjust font size here
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // API token input
            Text("API Token:", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = apiToken,
                onValueChange = { apiToken = it },
                label = { Text("API Token") },
                textStyle = TextStyle(fontSize = 20.sp),  // Adjust font size here
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Timer dropdown input
            Text("Push notification timer:", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))

            // Dropdown menu for timer options
            var expanded by remember { mutableStateOf(false) }
            Box(modifier = Modifier.fillMaxWidth()) {
                TextField(
                    value = selectedTimerOption,
                    onValueChange = { selectedTimerOption = it },
                    label = { Text("Timer Option") },
                    textStyle = TextStyle(fontSize = 20.sp),
                    modifier = Modifier.fillMaxWidth(),
                    readOnly = true,
                    trailingIcon = {
                        IconButton(onClick = { expanded = !expanded }) {
                            Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = null)
                        }
                    }
                )

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    timerOptions.forEach { option ->
                        DropdownMenuItem(
                            onClick = {
                                selectedTimerOption = option
                                expanded = false
                            },
                            text = { Text(option) },
                            modifier = Modifier.padding(8.dp),
                            leadingIcon = {
                                if (option == "Deactivated") {
                                    Icon(Icons.Default.ArrowDropDown, contentDescription = null)
                                }
                            },
                            trailingIcon = {
                                Icon(Icons.Default.ArrowDropDown, contentDescription = null)
                            },
                            enabled = true,
                            colors = MenuDefaults.itemColors(
                                disabledTextColor = Color.Gray
                            ),
                            contentPadding = PaddingValues(8.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }

        Button(
            onClick = {
                scope.launch {
                    context.dataStore.edit { preferences ->
                        preferences[Keys.API_TOKEN_KEY] = apiToken
                        preferences[Keys.HOMETOWN_KEY] = hometown
                        preferences[Keys.TIMER_OPTION_KEY] = selectedTimerOption // Save the timer option
                    }
                    delay(500)
                    onSave()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF1E88E5)
            )
        ) {
            Text("Save", color = Color.White)
        }
    }
}
