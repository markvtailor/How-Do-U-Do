package com.markvtls.howdud.domain.repositories

import com.markvtls.howdud.data.dto.Friend
import com.markvtls.howdud.data.dto.User
import com.markvtls.howdud.data.dto.UserChats
import com.markvtls.howdud.domain.model.Message
import kotlinx.coroutines.flow.Flow

interface FirestoreRepository {

    suspend fun getUsername(userId: String): Flow<User?>

    suspend fun getUserChats(userId: String): Flow<UserChats?>

    suspend fun getMessages(chatId: String): Flow<List<Message>>

    suspend fun getUserFriends(contacts: Map<String,String>): Flow<List<Friend>>

    fun saveUser(userId: String)

    fun sendNewMessage(chatId: String, message: Message)

    fun addNewChatToUserChats(userId: String, chatId: String)

}