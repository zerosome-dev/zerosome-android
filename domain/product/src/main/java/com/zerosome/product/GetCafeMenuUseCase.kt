package com.zerosome.product

import android.util.Log
import com.zerosome.domain.NetworkResult
import com.zerosome.domain.UseCase
import com.zerosome.domain.model.Cafe
import com.zerosome.domain.repository.HomeRepository
import javax.inject.Inject

class GetCafeMenuUseCase @Inject constructor(
    private val homeRepository: HomeRepository
): UseCase<List<Cafe>>(){

    override suspend fun innerLogic(): NetworkResult<List<Cafe>> = NetworkResult.Success(homeRepository.getCafe())
}