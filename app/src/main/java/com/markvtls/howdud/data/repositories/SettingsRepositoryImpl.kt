package com.markvtls.howdud.data.repositories

import com.markvtls.howdud.data.source.local.SettingsStore
import com.markvtls.howdud.domain.repositories.SettingsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SettingsRepositoryImpl @Inject constructor(
    private val settings: SettingsStore
): SettingsRepository {
    override suspend fun saveNotificationSettings(notificationSettings: Boolean) {
        settings.saveNotificationsSettingsToDataStore(notificationSettings)
    }

    override suspend fun saveVibrationSettings(vibrationSettings: String) {
        settings.saveVibrationSettingsToDataStore(vibrationSettings)
    }

    override suspend fun saveRingtoneSettings(ringtoneSettings: String) {
        settings.saveRingtoneSettingsToDataStore(ringtoneSettings)
    }

    override suspend fun saveStoreMediaSettings(storeMediaSettings: Boolean) {
        settings.saveStoreMediaSettingsToDataStore(storeMediaSettings)
    }

    override fun getNotificationSettings(): Flow<Boolean> {
        return settings.notificationsFlow
    }

    override fun getVibrationSettings(): Flow<String> {
        return settings.vibrationFlow
    }

    override fun getRingtoneSettings(): Flow<String> {
        return settings.ringtoneFlow
    }

    override fun getStoreMediaSettings(): Flow<Boolean> {
        return settings.storeMediaFlow
    }
}