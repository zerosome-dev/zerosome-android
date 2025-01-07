package com.zerosome.domain.category

import com.zerosome.domain.UseCase
import com.zerosome.domain.model.CategoryDepth1
import com.zerosome.domain.repository.CategoryRepository
import com.zerosome.domain.NetworkResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetCategoriesUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository
) : UseCase<List<CategoryDepth1>>() {

    private val _categoryFlow: MutableStateFlow<List<CategoryDepth1>> =
        MutableStateFlow(emptyList())
    private val categoryFlow = _categoryFlow.asStateFlow()

    init {
        +this
    }

    override suspend fun innerLogic(): NetworkResult<List<CategoryDepth1>> = run {
        val categories = categoryRepository.getAllCategories()
        NetworkResult.Success(categories).also {
            _categoryFlow.emit(categories)
        }
    }

    suspend fun getSpecificCategories(categoryDepth1Name: String): CategoryDepth1? =
        categoryFlow.single().find { it.categoryName == categoryDepth1Name }
}