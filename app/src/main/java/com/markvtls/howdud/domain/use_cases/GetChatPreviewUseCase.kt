package com.markvtls.howdud.domain.use_cases

import com.google.firebase.Timestamp
import com.markvtls.howdud.domain.model.Chat
import com.markvtls.howdud.domain.repositories.FirestoreRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetChatPreviewUseCase @Inject constructor(
    private val repository: FirestoreRepository
) {
    operator fun invoke(chatId: String, userId: String): Flow<Chat> = flow {
        val chatsPreview = mutableListOf<Chat>()

                    repository.getMessages(chatId).collect { messages ->
                        if (messages.isNotEmpty()) {
                            val icon = ""
                            val title = chatId.replace(userId,"").replace("_","")
                            val lastMessage = "${messages.last()?.author}: ${messages.last()?.text}"
                            val date = messages.last()?.date?.toDate().toString()
                            val newMessages = 0
                            val chat = Chat(icon, chatId, title, lastMessage, date, newMessages)
                            chatsPreview.add(chat)
                            emit(chat)
                        }

            }
        }
       /* val chatsPreview = mutableListOf<Chat>()
        repository.getUserChats(userId).collect {
    if (it != null) {
        println(it.chats)
        emit(chatsPreview)
    }*/
               /* it?.chats?.forEach { chatId ->
                    println("Test")
                    repository.getMessages(chatId).collect {
                        val icon = ""
                        val title = chatId.replace(userId,"").replace("_","")
                        val lastMessage = "${it.last()?.author}: ${it.last()?.text}"
                        val date = it.last()?.date?.toDate().toString()
                        val newMessages = 0
                        val chat = Chat(icon,title,lastMessage,date,newMessages)
                        chatsPreview.add(chat)
                    }
                }


            emit(chatsPreview)*/
        }

