package com.markvtls.howdud.domain.use_cases

import com.markvtls.howdud.data.dto.Friend
import com.markvtls.howdud.data.source.local.FirestoreDatabase
import com.markvtls.howdud.domain.repositories.FirestoreRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetUserFriendsUseCase @Inject constructor(
    private val repository: FirestoreRepository
) {
    suspend operator fun invoke(contacts: Map<String, String>): Flow<List<Friend>> = flow {


        repository.getUserFriends(contacts).collect {
            val signedFriends = it.filter { it.isUser }.distinctBy { it.id }
            emit(signedFriends)
        }

    }
}