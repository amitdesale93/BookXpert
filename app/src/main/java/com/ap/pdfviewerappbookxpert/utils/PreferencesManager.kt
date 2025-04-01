package com.ap.pdfviewerappbookxpert.utils

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore("settings")
private val NOTIFICATIONS_ENABLED = booleanPreferencesKey("notifications_enabled")

object PreferencesManager {

    suspend fun saveNotificationPreference(context: Context, isEnabled: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[NOTIFICATIONS_ENABLED] = isEnabled
        }
    }

    fun getNotificationPreference(context: Context): Flow<Boolean> {
        return context.dataStore.data.map { preferences ->
            preferences[NOTIFICATIONS_ENABLED] ?: true
        }
    }
}