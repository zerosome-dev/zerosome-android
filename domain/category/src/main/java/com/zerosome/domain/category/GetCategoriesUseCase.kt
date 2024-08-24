package com.zerosome.domain.category

import com.zerosome.domain.model.CategoryDepth1
import com.zerosome.domain.repository.CategoryRepository
import com.zerosome.network.NetworkResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetCategoriesUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository
){

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    private val categoryCache = MutableStateFlow<NetworkResult<List<CategoryDepth1>>>(NetworkResult.Loading)
    private val categoryFlow = MutableStateFlow<List<CategoryDepth1>>(emptyList())

    operator fun invoke() = categoryCache.also {
        innerLogic()
    }

    private fun innerLogic() {
        coroutineScope.launch {
            categoryRepository.getAllCategories().onEach {
                when (it) {
                    is NetworkResult.Loading -> categoryCache.emit(NetworkResult.Loading)
                    is NetworkResult.Success -> categoryCache.emit(NetworkResult.Success(it.data).also {
                        categoryFlow.emit(it.data)
                    })
                    is NetworkResult.Error -> categoryCache.emit(NetworkResult.Error(it.error))
                }
            }.collect()
        }
    }

    fun getSpecificCategory(code: String) = categoryFlow.map { it.find { category -> category.categoryCode == code } }
}