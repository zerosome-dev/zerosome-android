package com.zerosome.domain.category

import com.zerosome.domain.UseCase
import com.zerosome.domain.model.CategoryDepth1
import com.zerosome.domain.repository.CategoryRepository
import com.zerosome.domain.NetworkResult
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetCategoriesUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository
): UseCase<List<CategoryDepth1>>() {

//    private suspend fun innerLogic() {
//        coroutineScope.launch {
//            categoryRepository.getAllCategories().onEach {
//                when (it) {
//                    is NetworkResult.Loading -> categoryCache.emit(NetworkResult.Loading)
//                    is NetworkResult.Success -> categoryCache.emit(NetworkResult.Success(it.data).also {
//                        categoryFlow.emit(it.data)
//                    })
//                    is NetworkResult.Error -> categoryCache.emit(NetworkResult.Error(it.error))
//                }
//            }.collect()
//        }
//    }

    override suspend fun innerLogic(): NetworkResult<List<CategoryDepth1>> = run {
        val categories = categoryRepository.getAllCategories()
        NetworkResult.Success(categories)
    }

    fun getSpecificCategory(code: String) = categoryFlow.map { it.find { category -> category.categoryCode == code } }
}