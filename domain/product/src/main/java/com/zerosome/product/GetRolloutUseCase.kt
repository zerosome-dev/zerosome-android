package com.zerosome.product

import com.zerosome.domain.NetworkResult
import com.zerosome.domain.UseCase
import com.zerosome.domain.model.Rollout
import com.zerosome.domain.repository.HomeRepository
import javax.inject.Inject

class GetRolloutUseCase @Inject constructor(
    private val repository: HomeRepository
): UseCase<List<Rollout>>() {

    override suspend fun innerLogic(): NetworkResult<List<Rollout>> = NetworkResult.Success(repository.getRollout())
}