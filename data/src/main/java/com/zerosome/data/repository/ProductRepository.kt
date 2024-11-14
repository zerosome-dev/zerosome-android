package com.zerosome.data.repository

import com.zerosome.data.apiCall
import com.zerosome.data.mapper.domainModel
import com.zerosome.data.mapper.getAsDomainModel
import com.zerosome.datasource.remote.service.ProductService
import com.zerosome.domain.model.CategoryProduct
import com.zerosome.domain.model.Page
import com.zerosome.domain.model.Product
import com.zerosome.domain.model.SortItem
import com.zerosome.domain.repository.ProductRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class ProductRepositoryImpl @Inject constructor(
    private val productService: ProductService,
) : ProductRepository {

    override suspend fun getProductDetail(id: Int): Product = apiCall(
        block = {
            productService.getProductDetail(id)
        },
        transform = {
            it?.domainModel ?: throw com.zerosome.core.constants.ClientExceptions(com.zerosome.core.constants.ClientError.RESPONSE_NOT_VALIDATE_EXCEPTION)
        }
    )

    override suspend fun getProductsByCategory(
        categoryCode: String,
        offset: Int?,
        limit: Int?,
        orderType: SortItem,
        brandList: List<String>,
        zeroTagList: List<String>
    ): Page<CategoryProduct> = apiCall(
        block = {
            productService.getProductByCategory(
                d2CategoryCode = categoryCode,
                orderType = orderType.name,
                brandList = brandList,
                zeroCategoryList = zeroTagList,
                cursor = offset,
                limit = limit ?: 10
            )
        },
        transform = {
            it?.getAsDomainModel(contentTransformer = { content ->
                content.domainModel
            }) ?: throw com.zerosome.core.constants.ClientExceptions(com.zerosome.core.constants.ClientError.RESPONSE_NOT_VALIDATE_EXCEPTION)
        }
    )
}
