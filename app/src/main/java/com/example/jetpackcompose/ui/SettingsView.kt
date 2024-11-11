package com.example.jetpackcompose.ui

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map

// Extension function to create DataStore
val Context.dataStore by preferencesDataStore(name = "settings")

// Key for the API token
private val API_TOKEN_KEY = stringPreferencesKey("api_token_key")

@Composable
fun SettingsView() {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var apiToken by remember { mutableStateOf("") }

    // Load the saved API token on startup
    LaunchedEffect(Unit) {
        context.dataStore.data.map { preferences ->
            preferences[API_TOKEN_KEY] ?: ""
        }.collect {
            apiToken = it
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = apiToken,
            onValueChange = { apiToken = it },
            label = {
                Text(
                    text = "Enter API Token",
                    fontSize = 18.sp,                // Set font size
                    fontWeight = FontWeight.Bold     // Make text bold
                )
            },
            modifier = Modifier.fillMaxWidth(),

        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            scope.launch {
                // Save API token
                context.dataStore.edit { preferences ->
                    preferences[API_TOKEN_KEY] = apiToken
                }
            }
        }) {
            Text("Save")
        }
    }
}
