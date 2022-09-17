package com.markvtls.howdud.domain.use_cases

import com.markvtls.howdud.domain.repositories.FirestoreRepository
import javax.inject.Inject

class AddNewChatToUserChatsUseCase @Inject constructor(
    private val repository: FirestoreRepository
) {

    operator fun invoke(userId: String, chatId: String) {
        println("summon saving for $userId")
        repository.addNewChatToUserChats(userId, chatId)
    }
}