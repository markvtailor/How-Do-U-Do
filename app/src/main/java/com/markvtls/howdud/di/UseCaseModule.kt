package com.markvtls.howdud.di

import com.markvtls.howdud.domain.repositories.FirestoreRepository
import com.markvtls.howdud.domain.use_cases.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Singleton
    @Provides
    fun provideGetChatsListUseCase(firestoreRepository: FirestoreRepository): GetChatsListUseCase {
        return GetChatsListUseCase(firestoreRepository)
    }

    @Singleton
    @Provides
    fun provideGetChatMessagesUseCase(firestoreRepository: FirestoreRepository): GetChatMessagesUseCase {
        return GetChatMessagesUseCase(firestoreRepository)
    }

    @Singleton
    @Provides
    fun provideSaveNewUserUseCase(firestoreRepository: FirestoreRepository): SaveNewUserUseCase {
        return SaveNewUserUseCase(firestoreRepository)
    }

    @Singleton
    @Provides
    fun provideAddNewChatToUserChatsUseCase(firestoreRepository: FirestoreRepository): AddNewChatToUserChatsUseCase {
        return AddNewChatToUserChatsUseCase(firestoreRepository)
    }

    @Singleton
    @Provides
    fun provideGetUserFriendsUseCase(firestoreRepository: FirestoreRepository): GetUserFriendsUseCase {
        return GetUserFriendsUseCase(firestoreRepository)
    }

    @Singleton
    @Provides
    fun provideSendNewMessageUseCase(firestoreRepository: FirestoreRepository): SendNewMessage {
        return SendNewMessage(firestoreRepository)
    }
}