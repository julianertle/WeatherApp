// BottomNavBar.kt
package com.example.jetpackcompose.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

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
