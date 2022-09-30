package com.markvtls.howdud.domain.use_cases


import com.markvtls.howdud.domain.model.Chat
import com.markvtls.howdud.domain.model.lastMessageDate
import com.markvtls.howdud.domain.repositories.FirestoreRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetChatPreviewUseCase @Inject constructor(
    private val repository: FirestoreRepository
) {
    operator fun invoke(chatId: String, userId: String): Flow<Chat> = flow {


                    repository.getMessages(chatId).collect { messages ->
                        if (messages.isNotEmpty()) {
                            val icon = ""
                            val friendId = chatId.replace(userId,"").replace("_","")
                            getTitle(friendId).collect { title ->
                                val chatTitle = title ?:friendId
                                val lastMessageAuthor = if (messages.last().author == friendId) chatTitle else "Вы"
                                val lastMessage = "$lastMessageAuthor: ${messages.last().text}"
                                val date = messages.last().date.toDate().lastMessageDate()
                                val newMessages = 0
                                val chat = Chat(icon, chatId, chatTitle, lastMessage, date, newMessages)
                                println(chat.lastMessage)
                                emit(chat)
                            }

                        }



            }
        }

    private suspend fun getTitle(userId: String): Flow<String?> = flow {
        repository.getUsername(userId).collect {
            if (it != null ) {
                if (it.username.isBlank()) emit(null) else  emit(it.username)
            }
        }
    }

        }

