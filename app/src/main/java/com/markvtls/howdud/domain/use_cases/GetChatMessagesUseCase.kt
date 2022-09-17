package com.markvtls.howdud.domain.use_cases

import com.markvtls.howdud.domain.model.Message
import com.markvtls.howdud.domain.repositories.FirestoreRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetChatMessagesUseCase @Inject constructor(
    private val firestoreRepository: FirestoreRepository
) {
    operator fun invoke(chatId: String): Flow<List<Message?>> = flow {
        firestoreRepository.getMessages(chatId).collect {
            emit(it)
        }
    }
}