package com.zerosome.onboarding

import com.zerosome.domain.repository.UserRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SignUpUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    operator fun invoke(socialToken: String, socialType: String, nickname: String, marketingAgreed: Boolean) = userRepository.signUp(socialToken, socialType, nickname, marketingAgreed)
}