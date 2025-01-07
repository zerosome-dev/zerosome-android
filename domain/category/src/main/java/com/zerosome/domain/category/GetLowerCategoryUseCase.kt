package com.zerosome.domain.category

import com.zerosome.core.constants.ClientError
import com.zerosome.core.constants.ClientExceptions
import com.zerosome.domain.UseCase
import com.zerosome.domain.model.CategoryDepth2
import com.zerosome.domain.repository.CategoryRepository
import com.zerosome.domain.NetworkResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetLowerCategoryUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository
) : UseCase<List<CategoryDepth2>>() {

    private val _cacheFlow: MutableStateFlow<String> = MutableStateFlow("")
    private val cacheFlow = _cacheFlow.onEach {
        +this
    }

    init {
        cacheFlow.launchIn(coroutineScope)
    }

    operator fun plusAssign(categoryDepth1Name: String) {
        coroutineScope.launch {
            _cacheFlow.emit(categoryDepth1Name)
        }
    }

    override suspend fun innerLogic(): NetworkResult<List<CategoryDepth2>> = run {
        val categories = categoryRepository.getCategoryDepth2(_cacheFlow.value)
        NetworkResult.Success(categories.ifEmpty { throw ClientExceptions(ClientError.CATEGORY_NOT_AVAILABLE) })
    }
}