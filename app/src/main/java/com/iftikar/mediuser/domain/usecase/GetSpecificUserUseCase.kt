package com.iftikar.mediuser.domain.usecase

import com.iftikar.mediuser.domain.repository.UserRepository
import javax.inject.Inject

class GetSpecificUserUseCase @Inject constructor(private val userRepository: UserRepository) {
    operator fun invoke(userId: String) =
        userRepository.getSpecificUser(userId = userId)
}