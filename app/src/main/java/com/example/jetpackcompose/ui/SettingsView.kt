package com.example.jetpackcompose.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.*
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

val Context.dataStore by preferencesDataStore(name = "settings")
private val API_TOKEN_KEY = stringPreferencesKey("api_token_key")

@Composable
fun SettingsView() {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var apiToken by remember { mutableStateOf("") }
    var showInfoDialog by remember { mutableStateOf(false) }

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
        verticalArrangement = Arrangement.Top,  // Align items to the top
        horizontalAlignment = Alignment.Start  // Align horizontally to the start
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            TextField(
                value = apiToken,
                onValueChange = { apiToken = it },
                label = {
                    Text(
                        text = "Enter API Token",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                },
                textStyle = TextStyle(
                    fontSize = 22.sp,  // Make the entered text larger here
                    fontWeight = FontWeight.Normal
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )

            IconButton(onClick = { showInfoDialog = true }) {
                Icon(imageVector = Icons.Default.Info, contentDescription = "Info")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Centering the Save button horizontally
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center  // Centering the button horizontally
        ) {
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
                    Text("Click here to generate your own API Token from openweathermap.org", color = MaterialTheme.colorScheme.primary)
                }
            },
            confirmButton = {
                TextButton(onClick = { showInfoDialog = false }) {
                    Text("OK")
                }
            }
        )
    }
}
