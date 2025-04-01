package com.ap.pdfviewerappbookxpert.utils

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


private val Context.dataStore by preferencesDataStore(name = "all_prefs")

object DataStoreManager {
    private val NOTIFICATION_KEY = booleanPreferencesKey("is_notification_enabled")

    private val THEME_KEY = booleanPreferencesKey("dark_mode_enabled")
    private val themeJob = SupervisorJob()
    private val themeScope = CoroutineScope(Dispatchers.IO + themeJob)

    // Apply theme on app startup
    fun applyTheme(context: Context) {
        themeScope.launch {
            val isDarkMode : Boolean = getThemePreference(context).first()
            withContext(Dispatchers.Main) {
                val nightMode = if (isDarkMode) {
                    AppCompatDelegate.MODE_NIGHT_YES
                } else {
                    AppCompatDelegate.MODE_NIGHT_NO
                }
                AppCompatDelegate.setDefaultNightMode(nightMode)
            }
        }
    }



    // Save theme preference (Light/Dark)
    suspend fun saveThemePreference(context: Context, isDarkMode: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[THEME_KEY] = isDarkMode
        }
    }

    // Retrieve the stored theme preference
    /*fun getThemePreference(context: Context): Boolean {
        return runBlocking {
            context.dataStore.data.first()[THEME_KEY] ?: false
        }
    }*/

    fun getThemePreference(context: Context): Flow<Boolean> {
        return context.dataStore.data
            .map { preferences ->
                preferences[THEME_KEY] ?: false // Default to light mode
            }
    }



    // Save notification preference (ON/OFF)
    suspend fun saveNotificationPreference(context: Context, isOn: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[NOTIFICATION_KEY] = isOn
        }
    }
    fun getNotificationPreference(context: Context): Flow<Boolean> {
        return context.dataStore.data
            .map { preferences ->
                preferences[NOTIFICATION_KEY] ?: true // Default to ON
            }
    }
}