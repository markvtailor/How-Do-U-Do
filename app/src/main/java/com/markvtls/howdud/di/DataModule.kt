package com.markvtls.howdud.di

import android.content.Context
import com.markvtls.howdud.data.repositories.FirestoreRepositoryImpl
import com.markvtls.howdud.data.repositories.SettingsRepositoryImpl
import com.markvtls.howdud.data.source.local.FirestoreDatabase
import com.markvtls.howdud.data.source.local.SettingsStore
import com.markvtls.howdud.domain.repositories.FirestoreRepository
import com.markvtls.howdud.domain.repositories.SettingsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideSettingsStore(@ApplicationContext context: Context): SettingsStore {
        return SettingsStore(context)
    }

    @Provides
    @Singleton
    fun provideSettingsRepository(settingsStore: SettingsStore): SettingsRepository {
        return SettingsRepositoryImpl(settingsStore)
    }

    @Provides
    @Singleton
    fun provideFirestoreDatabase(): FirestoreDatabase {
        return FirestoreDatabase()
    }

    @Provides
    @Singleton
    fun provideFirestoreRepository(firestoreDatabase: FirestoreDatabase): FirestoreRepository {
        return FirestoreRepositoryImpl(firestoreDatabase)
    }
}