package com.zerosome.product

import com.zerosome.domain.repository.HomeRepository
import javax.inject.Inject

class GetRolloutUseCase @Inject constructor(
    private val repository: HomeRepository
) {

    operator fun invoke() = repository.getRollout()
}