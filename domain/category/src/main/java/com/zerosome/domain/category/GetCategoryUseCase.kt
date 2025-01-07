package com.zerosome.domain.category

import com.zerosome.core.constants.ClientError
import com.zerosome.core.constants.ClientExceptions
import com.zerosome.domain.UseCase
import com.zerosome.domain.model.CategoryDepth1
import com.zerosome.domain.NetworkResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.lastOrNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

class GetCategoryUseCase @Inject constructor(
    private val getCategoriesUseCase: GetCategoriesUseCase,
): UseCase<CategoryDepth1>(){
    private val codeFlow: MutableStateFlow<String> = MutableStateFlow("")
    private val cacheFlow = codeFlow.onEach {
        +this
    }.launchIn(coroutineScope)

    override suspend fun innerLogic(): NetworkResult<CategoryDepth1> = run {
        val item = getCategoriesUseCase().filter { it is NetworkResult.Success }.lastOrNull()
        (item as? NetworkResult.Success)?.data?.let {
            it.find { it.categoryCode == codeFlow.value }?.let { selectedCategory ->
                NetworkResult.Success(selectedCategory)
            } ?: NetworkResult.Error(ClientExceptions(ClientError.CATEGORY_NOT_AVAILABLE))
        } ?: NetworkResult.Error(ClientExceptions(ClientError.NETWORK_UNVALIDATED_INVALID))
    }

    operator fun plusAssign(code: String) {
        coroutineScope.launch {
            codeFlow.emit(code)
        }
    }
}