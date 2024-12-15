package com.example.jetpackcompose.data

import androidx.datastore.preferences.core.stringPreferencesKey

object Keys {
    val HOMETOWN_KEY = stringPreferencesKey("hometown_key")
    val API_TOKEN_KEY = stringPreferencesKey("api_token_key")
}
