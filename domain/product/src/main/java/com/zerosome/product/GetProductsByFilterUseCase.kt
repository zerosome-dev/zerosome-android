package com.zerosome.product

import com.zerosome.core.constants.ClientError
import com.zerosome.core.constants.ClientExceptions
import com.zerosome.domain.model.SortItem
import com.zerosome.domain.repository.ProductRepository
import com.zerosome.domain.NetworkResult
import com.zerosome.domain.UseCase
import com.zerosome.domain.model.CategoryProduct
import com.zerosome.domain.model.Page
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetProductsByFilterUseCase @Inject constructor(
    private val productRepository: ProductRepository
): UseCase<Page<CategoryProduct>>(){

    private data class RequestCache(
        val categoryCode: String = "",
        val orderType: SortItem = SortItem.RECENT,
        val brandList: List<String> = emptyList(),
        val tagList: List<String> = emptyList()
    )

    private val offsetFlow = MutableStateFlow<Int?>(null)
    private val _cacheFlow = MutableStateFlow<RequestCache?>(null)

    override suspend fun innerLogic(): NetworkResult<Page<CategoryProduct>> {
        val cache = _cacheFlow.single() ?: throw ClientExceptions(ClientError.PARAMETER_NOT_AVAILABLE)
        val categoryPageList = cache.let {
            productRepository.getProductsByCategory(
                categoryCode = it.categoryCode,
                offset = offsetFlow.value,
                limit = null,
                orderType = it.orderType,
                brandList = it.brandList,
                zeroTagList = it.tagList
            )
        }
        return NetworkResult.Success(categoryPageList)
    }

    operator fun plusAssign(categoryCode: String) {
        coroutineScope.launch {
            coroutineScope {
                _cacheFlow.emit(RequestCache(categoryCode))
            }
            coroutineScope {
                offsetFlow.emit(null)
            }
        }
    }


    fun setFilteringData(
        category2Code: String? = null,
        sortItem: SortItem? = null,
        brandList: List<String>? = null,
        zeroTagList: List<String>? = null
    ) {
        coroutineScope.launch {
            _cacheFlow.emit(
                _cacheFlow.value?.copy(
                    categoryCode = category2Code ?: _cacheFlow.value?.categoryCode ?: "",
                    orderType = sortItem ?: _cacheFlow.value?.orderType ?: SortItem.RECENT,
                    brandList = brandList ?: _cacheFlow.value?.brandList ?: emptyList(),
                    tagList = zeroTagList ?: _cacheFlow.value?.tagList ?: emptyList()
                )
            )
        }
    }
}