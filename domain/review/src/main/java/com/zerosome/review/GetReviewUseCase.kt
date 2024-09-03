package com.zerosome.review

import com.zerosome.domain.model.Review
import com.zerosome.domain.repository.ReviewRepository
import com.zerosome.network.NetworkResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetReviewUseCase @Inject constructor(
    private val repository: ReviewRepository
) {
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    private val _idFlow: MutableStateFlow<Int?> = MutableStateFlow(null)
    private val idFlow: StateFlow<Int> = _idFlow.filterNotNull().distinctUntilChanged().onEach {
        callLogic(true)
    }.stateIn(
        scope = coroutineScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = 0
    )

    init {
        idFlow.launchIn(coroutineScope)
    }

    private val _cursorFlow: MutableStateFlow<Int?> = MutableStateFlow(null)
    private val _listFlow: MutableStateFlow<List<Review>> = MutableStateFlow(emptyList())
    private val responseFlow: MutableStateFlow<NetworkResult<List<Review>>> =
        MutableStateFlow(NetworkResult.Loading)

    operator fun invoke(id: Int) = responseFlow.also {
        _idFlow.tryEmit(id)
    }

    fun getCurrentReviews() = responseFlow

    fun loadMore() = callLogic()

    fun refresh() = callLogic(true)

    private fun callLogic(isRefresh: Boolean = false) {
        coroutineScope.launch {
            repository.getReview(
                requireNotNull(_idFlow.value),
                if (isRefresh) null else _cursorFlow.value
            )
                .onEach {
                    when (it) {
                        is NetworkResult.Loading -> responseFlow.emit(NetworkResult.Loading)
                        is NetworkResult.Success -> {
                            val list = if (isRefresh) it.data else _listFlow.value.toMutableList().apply {
                                addAll(it.data)
                            }
                            responseFlow.emit(
                                NetworkResult.Success(list)
                            ).also {
                                _listFlow.emit(list)
                                _cursorFlow.emit(if (list.isNotEmpty()) list.last().reviewId else null)
                            }
                        }

                        is NetworkResult.Error -> responseFlow.emit(NetworkResult.Error(it.error))
                    }
                }.collect()
        }
    }
}