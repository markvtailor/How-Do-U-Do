package com.markvtls.howdud.domain.use_cases

import com.markvtls.howdud.domain.repositories.FirestoreRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class GetChatsListUseCase @Inject constructor(
    private val repository: FirestoreRepository
) {

     operator fun invoke(userId: String): Flow<List<String>> = flow {
        repository.getUserChats(userId).collect {
            if (it != null) {
                emit(it.chats)
            }
        }
    }
}