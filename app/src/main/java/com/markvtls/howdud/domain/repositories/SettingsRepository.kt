package com.markvtls.howdud.domain.repositories

import kotlinx.coroutines.flow.Flow

interface SettingsRepository {

    suspend fun saveNotificationSettings(notificationSettings: Boolean)

    suspend fun saveVibrationSettings(vibrationSettings: String)

    suspend fun saveRingtoneSettings(ringtoneSettings: String)

    suspend fun saveStoreMediaSettings(storeMediaSettings: Boolean)

    fun getNotificationSettings(): Flow<Boolean>

    fun getVibrationSettings(): Flow<String>

    fun getRingtoneSettings(): Flow<String>

    fun getStoreMediaSettings(): Flow<Boolean>

}