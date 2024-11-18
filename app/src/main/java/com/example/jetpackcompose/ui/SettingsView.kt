package com.example.jetpackcompose.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.ui.text.TextStyle
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map
import androidx.compose.material3.Card
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.Color

val Context.dataStore by preferencesDataStore(name = "settings")
private val API_TOKEN_KEY = stringPreferencesKey("api_token_key")
private val HOMETOWN_KEY = stringPreferencesKey("hometown_key")  // New key for hometown

@Composable
fun SettingsView(onSave: () -> Unit) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var apiToken by remember { mutableStateOf("") }
    var hometown by remember { mutableStateOf("") }  // State for hometown
    var showInfoDialog by remember { mutableStateOf(false) }

    // Load the saved values (API token and hometown) on startup
    LaunchedEffect(Unit) {
        context.dataStore.data.map { preferences ->
            val apiToken = preferences[API_TOKEN_KEY] ?: ""
            val hometown = preferences[HOMETOWN_KEY] ?: ""
            Pair(apiToken, hometown)
        }.collect {
            apiToken = it.first
            hometown = it.second
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally  // Centering content horizontally
    ) {
        // Hometown Section (Above API Token input)
        Text(
            text = "Enter your hometown:",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Hometown TextField inside the Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                TextField(
                    value = hometown,
                    onValueChange = { hometown = it },
                    textStyle = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Normal
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Hometown") }
                )
            }
        }

        // Input Section for API Token (same as before)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically, // Align text and icon vertically
            ) {
                Text(
                    text = "Enter API Token",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                )

                // Icon Button for info
                IconButton(onClick = { showInfoDialog = true }) {
                    Icon(imageVector = Icons.Default.Info, contentDescription = "Info")
                }
            }
        }



        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                TextField(
                    value = apiToken,
                    onValueChange = { apiToken = it },
                    textStyle = TextStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Normal
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("API Token") }
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Save Button with better design
        Button(
            onClick = {
                scope.launch {
                    // Save API token and hometown
                    context.dataStore.edit { preferences ->
                        preferences[API_TOKEN_KEY] = apiToken
                        preferences[HOMETOWN_KEY] = hometown  // Save hometown
                    }
                    // Wait for 0.5 seconds before switching
                    delay(500) // 500 milliseconds delay
                    // Trigger onSave callback to navigate to Home
                    onSave()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
            shape = RoundedCornerShape(50.dp),  // Rounded corners for the button
            colors = ButtonDefaults.buttonColors(
                Color(0xFF1E88E5)  // Set background color
            )
        ) {
            Text(
                text = "Save",
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(8.dp),
            )
        }
    }

    // Info dialog with clickable link
    if (showInfoDialog) {
        AlertDialog(
            onDismissRequest = { showInfoDialog = false },
            title = { Text(text = "API Token Info") },
            text = {
                TextButton(onClick = {
                    val url = "https://home.openweathermap.org/users/sign_in"
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    context.startActivity(intent)
                }) {
                    Text(
                        text = "Click here to generate your own API Token from openweathermap.org",
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            },
            confirmButton = {
                TextButton(onClick = { showInfoDialog = false }) {
                    Text("OK")
                }
            },
            modifier = Modifier.padding(16.dp)
        )
    }
}