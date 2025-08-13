package com.iftikar.mediuser.domain.usecase

import com.iftikar.mediuser.domain.repository.UserRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val userRepository: UserRepository) {
    suspend operator fun invoke(email: String, password: String) =
        userRepository.login(email, password)
}