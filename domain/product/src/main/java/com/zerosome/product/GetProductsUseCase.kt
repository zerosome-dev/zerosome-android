package com.zerosome.product

import com.zerosome.domain.repository.ProductRepository
import javax.inject.Inject

class GetProductsUseCase @Inject constructor(
    private val productRepository: ProductRepository
) {
    operator fun invoke(id: String) = productRepository.getProductDetail(id)
}