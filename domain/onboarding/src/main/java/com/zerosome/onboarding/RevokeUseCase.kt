package com.zerosome.onboarding

import com.zerosome.domain.repository.UserRepository
import javax.inject.Inject

class RevokeUseCase @Inject constructor(
    private val userRepository: UserRepository
) {

    operator fun invoke() = userRepository.revoke()
}