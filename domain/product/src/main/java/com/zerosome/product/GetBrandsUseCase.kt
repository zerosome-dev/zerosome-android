package com.zerosome.product

import com.zerosome.domain.UseCase
import com.zerosome.domain.repository.FilterRepository
import com.zerosome.network.NetworkResult
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

class GetBrandsUseCase @Inject constructor(
    private val repository: FilterRepository
) : UseCase() {
    private val cacheFlow = repository.getBrands().stateIn(
        coroutineScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = NetworkResult.Loading
    )

    init {
        innerLogic()
    }

    operator fun invoke() = cacheFlow

    override fun innerLogic() {
        cacheFlow.launchIn(coroutineScope)
    }
}