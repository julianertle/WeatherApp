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
import androidx.compose.material3.CardElevation
import androidx.compose.ui.text.TextStyle
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map
import androidx.compose.material3.Card
import androidx.compose.ui.unit.dp

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
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally  // Centering content horizontally
    ) {
        // Input Section in a Card with elevation

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 10.dp
            ),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Enter API Token",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                // TextField inside the Card
                TextField(
                    value = apiToken,
                    onValueChange = { apiToken = it },
                    textStyle = TextStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Normal
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        // Icon Button for info
        IconButton(onClick = { showInfoDialog = true }) {
            Icon(imageVector = Icons.Default.Info, contentDescription = "Info")
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Save Button with better design
        Button(
            onClick = {
                scope.launch {
                    // Save API token
                    context.dataStore.edit { preferences ->
                        preferences[API_TOKEN_KEY] = apiToken
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
            shape = RoundedCornerShape(50.dp)  // Rounded corners for the button
        ) {
            Text(
                text = "Save",
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(8.dp)
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
