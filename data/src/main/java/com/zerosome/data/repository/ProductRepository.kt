package com.zerosome.data.repository

import com.zerosome.data.mapper.domainModel
import com.zerosome.data.mapper.mapToDomain
import com.zerosome.datasource.local.source.TokenSource
import com.zerosome.datasource.remote.service.ProductService
import com.zerosome.domain.model.Product
import com.zerosome.domain.repository.ProductRepository
import com.zerosome.network.NetworkResult
import com.zerosome.network.safeCall
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
internal class ProductRepositoryImpl @Inject constructor(
    private val productService: ProductService,
    tokenSource: TokenSource
) : ProductRepository {

    private val currentAccessToken = tokenSource.getAccessToken().stateIn(
        scope = CoroutineScope(Dispatchers.IO),
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = null
    )

    init {
        CoroutineScope(Dispatchers.IO)
            .launch {
                currentAccessToken.collect()
            }
    }

    override fun getProductDetail(id: String): Flow<NetworkResult<Product>> =
        productService.getProductDetail(
            token = currentAccessToken.value?.accessToken,
            productId = id
        ).mapToDomain { it.domainModel }
}
