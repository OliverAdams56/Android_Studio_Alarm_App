package com.example.wakeup.data

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


private const val THEME_PREFERENCES_NAME = "theme_preferences"
private val Context.dataStore by preferencesDataStore(name = THEME_PREFERENCES_NAME)

object ThemePreference
{
    private val DARK_THEME_KEY = booleanPreferencesKey("dark_theme")
    private val ALARM_TIME_KEY = stringPreferencesKey("alarm_time")

    // Function to read the theme from DataStore
    val Context.themePreference: Flow<Boolean>
        get() = dataStore.data.map { preferences ->
            preferences[DARK_THEME_KEY] ?: false // Default to light theme
        }

    // Function to save the theme to DataStore
    suspend fun Context.setThemePreference(darkTheme: Boolean)
    {
        dataStore.edit { preferences ->
            preferences[DARK_THEME_KEY] = darkTheme
        }
    }

    // Function to read the alarm time from DataStore
    val Context.alarmTimePreference: Flow<String>
        get() = dataStore.data.map { preferences ->
                preferences[ALARM_TIME_KEY] ?: "Set Alarm Time" // Default alarm time
            }

    // Function to save the alarm time to DataStore
    suspend fun Context.setAlarmTimePreference(alarmTime: String)
    {
        dataStore.edit { preferences ->
            preferences[ALARM_TIME_KEY] = alarmTime
        }
    }

    // Function to clear the alarm time from DataStore
    suspend fun Context.clearAlarmTimePreference()
    {
        dataStore.edit { preferences ->
            preferences.remove(ALARM_TIME_KEY)
        }
    }
}
