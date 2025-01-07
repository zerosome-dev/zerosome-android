package com.zerosome.review

import com.zerosome.core.constants.ClientError
import com.zerosome.core.constants.ClientExceptions
import com.zerosome.domain.model.Review
import com.zerosome.domain.repository.ReviewRepository
import com.zerosome.domain.NetworkResult
import com.zerosome.domain.UseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.flow.singleOrNull
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetReviewUseCase @Inject constructor(
    private val repository: ReviewRepository
) : UseCase<List<Review>>() {

    private val _idFlow: MutableStateFlow<Int?> = MutableStateFlow(null)
    private val idFlow: StateFlow<Int> = _idFlow.filterNotNull().distinctUntilChanged().onEach {
        innerLogic()
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

    operator fun plusAssign(id: Int) {
        coroutineScope.launch {
            _idFlow.emit(id)
        }
    }

    suspend fun getCurrentReviews() = _listFlow.single()

    fun loadMore() {
        coroutineScope.launch {
            innerLogic()
        }
    }

    override fun refresh() {
        coroutineScope.launch {
            coroutineScope {
                _cursorFlow.emit(null)
            }
            coroutineScope {
                _listFlow.emit(emptyList())
            }
        }.invokeOnCompletion {
            coroutineScope.launch {
                innerLogic()
            }
        }
    }

    override suspend fun innerLogic(): NetworkResult<List<Review>> {
        val cursor = _cursorFlow.singleOrNull()
        val list = _listFlow.single()
        val id = _idFlow.single() ?: throw ClientExceptions(ClientError.PARAMETER_NOT_AVAILABLE)
        return repository.getReview(id, cursor).let {
            coroutineScope {
                if (list.isNotEmpty()) {
                    _listFlow.emit(mutableListOf<Review>().apply {
                        addAll(it)
                        addAll(list)
                    })
                } else {
                    _listFlow.emit(it)
                }
            }
            NetworkResult.Success(_listFlow.single())
        }
    }
}