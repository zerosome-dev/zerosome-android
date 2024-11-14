package com.zerosome.domain.category

import com.zerosome.core.constants.ClientExceptions
import com.zerosome.domain.UseCase
import com.zerosome.domain.model.CategoryDepth1
import com.zerosome.domain.NetworkResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.lastOrNull
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

class GetCategoryUseCase @Inject constructor(
    private val getCategoriesUseCase: GetCategoriesUseCase,
): UseCase<CategoryDepth1>(){
    private val codeFlow: MutableStateFlow<String> = MutableStateFlow("")
    private val cacheFlow = codeFlow.onEach {
        +this
    }

    override suspend fun innerLogic(): NetworkResult<CategoryDepth1> {
        val item = getCategoriesUseCase().filter { it is NetworkResult.Success }.lastOrNull()
        (item as? NetworkResult.Success)?.data?.let {

        } ?: NetworkResult.Error(ClientExceptions())
    }

    operator fun plusAssign(code: String) {
        coroutineScope.launch {
            codeFlow.emit(code)
        }
    }
}