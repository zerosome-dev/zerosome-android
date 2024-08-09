package com.zerosome.domain.category

import com.zerosome.domain.repository.CategoryRepository
import javax.inject.Inject

class GetAllCategoriesUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository
){

    operator fun invoke() = categoryRepository.getAllCategories()
}