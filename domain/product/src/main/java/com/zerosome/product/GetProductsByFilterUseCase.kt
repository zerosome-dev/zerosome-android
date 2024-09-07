package com.zerosome.product

import com.zerosome.domain.model.SortItem
import com.zerosome.domain.repository.ProductRepository
import com.zerosome.network.NetworkResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@OptIn(ExperimentalCoroutinesApi::class)
@Singleton
class GetProductsByFilterUseCase @Inject constructor(
    private val productRepository: ProductRepository
) {
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    private data class RequestCache(
        val categoryCode: String = "",
        val orderType: SortItem = SortItem.RECENT,
        val brandList: List<String> = emptyList(),
        val tagList: List<String> = emptyList()
    )

    private val offsetFlow = MutableStateFlow<Int?>(null)
    private val offsetCountFlow = MutableSharedFlow<Int>()
    private val _cacheFlow = MutableStateFlow<RequestCache?>(null)
    private val cacheFlow = combine(
        _cacheFlow.filterNotNull().distinctUntilChanged(),
        offsetCountFlow.distinctUntilChanged()
    ) { cache, count ->
        cache to count
    }.flatMapLatest { (cache, _) ->
        productRepository.getProductsByCategory(
            categoryCode = cache.categoryCode,
            offset = offsetFlow.value,
            limit = null,
            orderType = cache.orderType,
            brandList = cache.brandList,
            zeroTagList = cache.tagList
        ).map { result ->
            when (result) {
                is NetworkResult.Loading -> NetworkResult.Loading
                is NetworkResult.Success -> NetworkResult.Success(result.data.page).also {
                    offsetFlow.emit(if (result.data.page.isEmpty()) null else result.data.offset)
                }

                is NetworkResult.Error -> NetworkResult.Error(result.error)
            }
        }
    }

    operator fun invoke(categoryCode: String) = cacheFlow.also {
        coroutineScope.launch {
            _cacheFlow.emit(RequestCache(categoryCode))
            offsetFlow.emit(null)
            offsetCountFlow.emit(0)
        }
    }

    fun loadMore() {
        coroutineScope.launch {
            offsetCountFlow.emit(offsetCountFlow.first() + 1)
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