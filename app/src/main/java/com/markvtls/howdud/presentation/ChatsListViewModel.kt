package com.markvtls.howdud.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.markvtls.howdud.data.dto.Friend
import com.markvtls.howdud.domain.model.Chat
import com.markvtls.howdud.domain.repositories.FirestoreRepository
import com.markvtls.howdud.domain.use_cases.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ChatsListViewModel @Inject constructor(
    private val getChatsListUseCase: GetChatsListUseCase,
    private val getChatMessagesUseCase: GetChatMessagesUseCase,
    private val saveNewUserUseCase: SaveNewUserUseCase,
    private val addNewChatToUserChatsUseCase: AddNewChatToUserChatsUseCase,
    private val getUserFriendsUseCase: GetUserFriendsUseCase,
    private val getChatsPreviewUseCase: GetChatPreviewUseCase

) : ViewModel() {

    private val _friends = MutableStateFlow<List<Friend>>(emptyList())
    val friends get() = _friends
    private val chatsBuffer = mutableListOf<Chat>()
    private var _chats = MutableStateFlow<List<Chat>>(mutableListOf())
    val chats get() = _chats
   /* init {
        getUserChats("admin")
    }*/


     fun getUserChats(userId: String) {
        viewModelScope.launch {
            getChatsListUseCase(userId)
            chats.collect {
                it.forEach { println(it.newMessages) }
               // it.forEach { getChatMessages(it) }
            }
        }
    }

    private fun getPreview(chatId: String, userId: String) {
        viewModelScope.launch {
            getChatsPreviewUseCase(chatId, userId).collect { chat ->
                _friends.collect {
                    println(it)
                    chat.title = friends.value.find { it.id == chat.title }?.name ?: chat.title
                    println(friends.value.find { it.id == chat.title }?.name ?: chat.title)
                    println(chat.id)

                    chatsBuffer.add(chat)
                    println(chatsBuffer.distinctBy { it.id })
                    //_chats.emit(chatsList)
                    _chats.emit(chatsBuffer.distinctBy { it.id })
                }
            }
        }

    }
    fun getChatsPreview(userId: String) {
        viewModelScope.launch {
            getChatsListUseCase(userId).collect {
                it.forEach {
                    getPreview(it, userId)
                }
                chatsBuffer.clear()
            }

        }
    }

    fun getChatMessages(chatId: String) {
        viewModelScope.launch {
            getChatMessagesUseCase(chatId).collect {
                it.forEach {
                    if (it != null) {
                        println(it.text)
                    }
                }
            }
        }
    }

    fun getUserFriends(contacts: Map<String, String>) {
        contacts.forEach { println("${it.value} - ${it.key}") }
        viewModelScope.launch {
                getUserFriendsUseCase(contacts).collect {
                    _friends.emit(it)
                }
            }
        }

    fun createNewChat(userId: String, friendId: String) {
        val chatId = userId+"_"+friendId
        viewModelScope.launch {
            addNewChatToUserChatsUseCase(userId, chatId)
                .also { addNewChatToUserChatsUseCase(friendId, chatId) }
        }
    }


    fun saveNewUser(userId: String) {
        viewModelScope.launch {
            saveNewUserUseCase(userId)
        }
    }
}