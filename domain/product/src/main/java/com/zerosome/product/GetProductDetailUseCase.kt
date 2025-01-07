package com.zerosome.product

import com.zerosome.core.constants.ClientError
import com.zerosome.core.constants.ClientExceptions
import com.zerosome.domain.model.Product
import com.zerosome.domain.repository.ProductRepository
import com.zerosome.domain.NetworkResult
import com.zerosome.domain.UseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetProductDetailUseCase @Inject constructor(
    private val productRepository: ProductRepository
): UseCase<Product>() {

    private val _idFlow: MutableStateFlow<Int?> = MutableStateFlow(null)
    private val dataFlow: MutableStateFlow<Product?> = MutableStateFlow(null)

    override suspend fun innerLogic(): NetworkResult<Product> = run {
        val id = _idFlow.single() ?: throw ClientExceptions(ClientError.PARAMETER_NOT_AVAILABLE)
        val currentProduct = productRepository.getProductDetail(id)
        NetworkResult.Success(currentProduct).also { dataFlow.emit(it.data) }
    }

    operator fun plusAssign(id: Int) {
        coroutineScope.launch {
            _idFlow.emit(id)
        }
    }

    suspend fun getCurrentProduct() = dataFlow.single()
}