package com.markvtls.howdud.domain.use_cases

import com.markvtls.howdud.domain.repositories.FirestoreRepository
import javax.inject.Inject

class SaveNewUserUseCase @Inject constructor(
    private val repository: FirestoreRepository
) {

    operator fun invoke(userId: String) {
        val id = userId.substring(1)
        repository.saveUser(id)
    }

}