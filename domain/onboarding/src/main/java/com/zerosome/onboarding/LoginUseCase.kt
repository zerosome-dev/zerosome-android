package com.zerosome.onboarding

import com.zerosome.domain.repository.UserRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginUseCase @Inject constructor(
    private val userRepository: UserRepository,
){
    operator fun invoke(socialType: String, socialToken: String) = userRepository.login(socialToken, socialType)
}