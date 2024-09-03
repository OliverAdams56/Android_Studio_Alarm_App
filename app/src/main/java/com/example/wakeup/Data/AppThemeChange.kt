package com.example.wakeup.Data

import android.content.Context
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private const val THEME_PREFERENCES_NAME = "theme_preferences"
private val Context.dataStore by preferencesDataStore(name = THEME_PREFERENCES_NAME)

object ThemePreference {
    private val DARK_THEME_KEY = booleanPreferencesKey("dark_theme")

    // Function to read the theme from DataStore
    val Context.themePreference: Flow<Boolean>
        get() = dataStore.data.map { preferences ->
            preferences[DARK_THEME_KEY] ?: false // Default to light theme
        }

    // Function to save the theme to DataStore
    suspend fun Context.setThemePreference(darkTheme: Boolean) {
        dataStore.edit { preferences ->
            preferences[DARK_THEME_KEY] = darkTheme
        }
    }
}
