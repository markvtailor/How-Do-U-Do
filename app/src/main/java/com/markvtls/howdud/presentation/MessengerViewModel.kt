package com.markvtls.howdud.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.markvtls.howdud.domain.model.Message
import com.markvtls.howdud.domain.use_cases.GetChatMessagesUseCase
import com.markvtls.howdud.domain.use_cases.SendNewMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MessengerViewModel @Inject constructor(
    private val getChatMessagesUseCase: GetChatMessagesUseCase,
    private val sendNewMessage: SendNewMessage
): ViewModel() {

    private val _messages = MutableStateFlow<List<Message?>>(emptyList())
    val messages: StateFlow<List<Message?>> get() = _messages

    init {
        //getChatMessagesUseCase()
    }

    fun loadMessages(chatId: String) {
        viewModelScope.launch {
            getChatMessagesUseCase(chatId).collect {
                _messages.emit(it)
            }
        }
    }


    fun sendMessage(chatId: String, message: Message) {
        viewModelScope.launch {
            sendNewMessage(chatId, message)
        }
    }

}