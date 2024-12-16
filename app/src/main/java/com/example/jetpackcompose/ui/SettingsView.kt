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

val Context.dataStore by preferencesDataStore(name = "settings")

@Composable
fun SettingsView(onSave: () -> Unit) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var apiToken by remember { mutableStateOf("") }
    var hometown by remember { mutableStateOf("") }
    var showInfoDialog by remember { mutableStateOf(false) }

    // Load the saved values
    LaunchedEffect(Unit) {
        context.dataStore.data.map { preferences ->
            val apiToken = preferences[Keys.API_TOKEN_KEY] ?: ""
            val hometown = preferences[Keys.HOMETOWN_KEY] ?: ""
            Pair(apiToken, hometown)
        }.collect {
            apiToken = it.first
            hometown = it.second
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
            Text("Enter your hometown:", fontSize = 20.sp, fontWeight = FontWeight.Bold)
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
            Text("Enter API Token:", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = apiToken,
                onValueChange = { apiToken = it },
                label = { Text("API Token") },
                textStyle = TextStyle(fontSize = 20.sp),  // Adjust font size here
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))
        }

        Button(
            onClick = {
                scope.launch {
                    context.dataStore.edit { preferences ->
                        preferences[Keys.API_TOKEN_KEY] = apiToken
                        preferences[Keys.HOMETOWN_KEY] = hometown
                    }
                    delay(500)
                    onSave()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter), // Align button at the bottom
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF1E88E5) // Set the button color
            )
        ) {
            Text("Save", color = Color.White) // Set text color to white for better contrast
        }

    }
}
