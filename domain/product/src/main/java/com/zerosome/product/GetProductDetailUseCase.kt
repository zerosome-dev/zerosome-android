package com.zerosome.product

import com.zerosome.domain.model.Product
import com.zerosome.domain.repository.ProductRepository
import com.zerosome.network.NetworkResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetProductDetailUseCase @Inject constructor(
    private val productRepository: ProductRepository
) {

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    private val _idFlow: MutableStateFlow<Int?> = MutableStateFlow(null)
    private val responseFlow: MutableStateFlow<NetworkResult<Product>> =
        MutableStateFlow(NetworkResult.Loading)

    operator fun invoke(id: Int) = responseFlow.also {
        _idFlow.tryEmit(id)
    }.also { callLogic() }

    private fun callLogic() {
        coroutineScope.launch {
            productRepository.getProductDetail(requireNotNull(_idFlow.value))
                .onEach {
                    when (it) {
                        is NetworkResult.Loading -> responseFlow.emit(NetworkResult.Loading)
                        is NetworkResult.Success -> responseFlow.emit(NetworkResult.Success(it.data))
                        is NetworkResult.Error -> responseFlow.emit(NetworkResult.Error(it.error))
                    }
                }.collect()
        }
    }

    fun refresh() = callLogic()

    fun getCurrentProduct() = responseFlow
}