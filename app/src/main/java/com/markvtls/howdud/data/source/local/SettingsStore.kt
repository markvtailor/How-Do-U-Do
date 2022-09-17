package com.markvtls.howdud.data.source.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException


private const val SETTINGS = "SETTINGS"
private val NOTIFICATIONS = booleanPreferencesKey("notifications_settings")
private val VIBRATION = stringPreferencesKey("vibration_settings")
private val RINGTONE = stringPreferencesKey("ringtone_settings")
private val STORE_MEDIA = booleanPreferencesKey("store_media_settings")


private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = SETTINGS
)


class SettingsStore(@ApplicationContext context: Context) {

    private val dataStore = context.dataStore


    suspend fun saveNotificationsSettingsToDataStore(notificationSettings: Boolean) {
        dataStore.edit { settings ->
            settings[NOTIFICATIONS] = notificationSettings
        }
    }

    suspend fun saveVibrationSettingsToDataStore(vibrationSettings: String) {
        dataStore.edit { settings ->
            settings[VIBRATION] = vibrationSettings
        }
    }

    suspend fun saveRingtoneSettingsToDataStore(ringtoneSettings: String) {
        dataStore.edit { settings ->
            settings[RINGTONE] = ringtoneSettings
        }
    }

    suspend fun saveStoreMediaSettingsToDataStore(storeMediaSettings: Boolean) {
        dataStore.edit { settings ->
            settings[STORE_MEDIA] = storeMediaSettings
        }
    }


    val notificationsFlow: Flow<Boolean> = dataStore.data
        .catch {
            if (it is IOException) {
                it.printStackTrace()
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .map { preferences ->
            preferences[NOTIFICATIONS] ?: true
        }

    val vibrationFlow: Flow<String> = dataStore.data
        .catch {
            if (it is IOException) {
                it.printStackTrace()
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .map { preferences ->
            preferences[VIBRATION] ?: "Default"
        }

    val ringtoneFlow: Flow<String> = dataStore.data
        .catch {
            if (it is IOException) {
                it.printStackTrace()
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .map { preferences ->
            preferences[RINGTONE] ?: "Default"
        }

    val storeMediaFlow: Flow<Boolean> = dataStore.data
        .catch {
            if (it is IOException) {
                it.printStackTrace()
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .map { preferences ->
            preferences[STORE_MEDIA] ?: true
        }
}