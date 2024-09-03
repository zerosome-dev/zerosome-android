package com.zerosome.data.repository

import com.zerosome.data.mapper.domainModel
import com.zerosome.data.mapper.mapToDomain
import com.zerosome.datasource.remote.service.ProductService
import com.zerosome.domain.model.CategoryProduct
import com.zerosome.domain.model.Page
import com.zerosome.domain.model.Product
import com.zerosome.domain.model.SortItem
import com.zerosome.domain.repository.ProductRepository
import com.zerosome.network.NetworkResult
import com.zerosome.network.safeCall
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
internal class ProductRepositoryImpl @Inject constructor(
    private val productService: ProductService,
) : ProductRepository {

    override fun getProductDetail(id: Int): Flow<NetworkResult<Product>> = safeCall {
        productService.getProductDetail(
            productId = id
        )
    }.mapToDomain { it.domainModel }

    override fun getProductsByCategory(
        categoryCode: String,
        offset: Int?,
        limit: Int?,
        orderType: SortItem,
        brandList: List<String>,
        zeroTagList: List<String>
    ): Flow<NetworkResult<Page<CategoryProduct>>> = safeCall {
        productService.getProductByCategory(
            d2CategoryCode = categoryCode,
            orderType = orderType.name,
            brandList = brandList,
            zeroCategoryList = zeroTagList,
            cursor = offset,
            limit = limit ?: 10
        )
    }.mapToDomain { Page(it.content.map { content -> content.domainModel }, offset = it.offset, limit = it.limit) }

}
