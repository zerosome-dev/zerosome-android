package com.zerosome.product

import com.zerosome.domain.UseCase
import com.zerosome.domain.repository.FilterRepository
import com.zerosome.domain.NetworkResult
import com.zerosome.domain.model.ZeroCategory
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

class GetFilterUseCase @Inject constructor(
    private val repository: FilterRepository
): UseCase<List<ZeroCategory>>() {

    override suspend fun innerLogic() = NetworkResult.Success(repository.getZeroTag())
}