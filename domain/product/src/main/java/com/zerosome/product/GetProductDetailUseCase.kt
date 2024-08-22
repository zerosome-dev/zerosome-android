package com.zerosome.product

import com.zerosome.domain.model.Product
import com.zerosome.domain.repository.ProductRepository
import com.zerosome.network.NetworkResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetProductDetailUseCase @Inject constructor(
    private val productRepository: ProductRepository
) {

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    private val _idFlow: MutableStateFlow<Int?> = MutableStateFlow(null)
    private val responseFlow: StateFlow<NetworkResult<Product>> =
        _idFlow.filterNotNull().distinctUntilChanged().flatMapLatest {
            productRepository.getProductDetail(it)
        }.stateIn(
            coroutineScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = NetworkResult.Loading
        )


    operator fun invoke(id: Int) = responseFlow.also {
        _idFlow.tryEmit(id)
    }

    fun getCurrentProduct() = responseFlow
}