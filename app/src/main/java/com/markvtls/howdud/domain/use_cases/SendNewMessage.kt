package com.markvtls.howdud.domain.use_cases

import com.markvtls.howdud.domain.model.Message
import com.markvtls.howdud.domain.repositories.FirestoreRepository
import javax.inject.Inject

class SendNewMessage @Inject constructor(
    private val repository: FirestoreRepository
) {
    operator fun invoke(chatId: String, message: Message) {
        repository.sendNewMessage(chatId, message)
    }
}