package com.markvtls.howdud.data.repositories

import com.markvtls.howdud.data.dto.Friend
import com.markvtls.howdud.data.dto.UserChats
import com.markvtls.howdud.data.source.local.FirestoreDatabase
import com.markvtls.howdud.domain.model.Message
import com.markvtls.howdud.domain.repositories.FirestoreRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FirestoreRepositoryImpl @Inject constructor(
    private val firestoreDatabase: FirestoreDatabase
): FirestoreRepository {

    override suspend fun getUserChats(userId: String): Flow<UserChats?> {
        return firestoreDatabase.getChatsList(userId)
    }

    override suspend fun getMessages(chatId: String): Flow<List<Message?>> {
        return firestoreDatabase.getMessages(chatId)
    }

    override suspend fun getUserFriends(contacts: Map<String,String>): Flow<List<Friend>> {
        return firestoreDatabase.getSignedFriends(contacts)
    }

    override fun saveUser(userId: String) {
        firestoreDatabase.saveNewUser(userId)
    }

    override fun sendNewMessage(chatId: String, message: Message) {
        firestoreDatabase.sendNewMessage(chatId, message)
    }

    override fun addNewChatToUserChats(userId: String, chatId: String) {
        firestoreDatabase.addChatToUserChats(userId, chatId)
    }
}