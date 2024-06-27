package com.zerosome.data.repository

import com.zerosome.data.dto.response.ProductResponse
import com.zerosome.data.service.ProductService
import com.zerosome.network.NetworkResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton
//
//interface ProductRepository {
//
//    fun getProductList()
//}
//
//@Singleton
//internal class ProductRepositoryImpl @Inject constructor(
//    private val productService: ProductService
//): ProductRepository {
//    override fun getProductList(): Flow<NetworkResult<List<ProductResponse>>> = productService.getProductDetail()
//    }
//}
