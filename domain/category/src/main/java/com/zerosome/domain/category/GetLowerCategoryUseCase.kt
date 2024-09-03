package com.zerosome.domain.category

import com.zerosome.domain.UseCase
import com.zerosome.domain.model.CategoryDepth2
import com.zerosome.domain.repository.CategoryRepository
import com.zerosome.network.NetworkResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetLowerCategoryUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository
) : UseCase() {

    private val _categoryFlow = MutableStateFlow("")

    private val responseFlow: MutableStateFlow<NetworkResult<List<CategoryDepth2>>> =
        MutableStateFlow(NetworkResult.Loading)

    operator fun invoke(categoryDepth1Name: String) = responseFlow.also {
        coroutineScope.launch {
            _categoryFlow.emit(categoryDepth1Name)
        }.also {
            innerLogic()
        }
    }

    override fun innerLogic() {
        coroutineScope.launch {
            categoryRepository.getCategoryDepth2(_categoryFlow.value).onEach {
                when (it) {
                    is NetworkResult.Loading -> responseFlow.emit(NetworkResult.Loading)
                    is NetworkResult.Success -> responseFlow.emit(NetworkResult.Success(it.data))
                    is NetworkResult.Error -> responseFlow.emit(NetworkResult.Error(it.error))
                }
            }.collect()
        }
    }
}