package com.zerosome.domain

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

open class UseCase<T> {
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        _itemFlow.update { NetworkResult.Error(throwable) }
    }

    val coroutineScope = CoroutineScope(Dispatchers.IO + exceptionHandler)


    private val _itemCallFlow = MutableSharedFlow<Unit>()
    private val itemCallFlow = _itemCallFlow.asSharedFlow().onEach {
        callLogic()
    }

    private val _itemFlow = MutableStateFlow<NetworkResult<T>>(NetworkResult.Loading)

    open operator fun invoke() = _itemFlow

    open suspend fun innerLogic(): NetworkResult<T> = NetworkResult.Loading

    init {
        itemCallFlow.shareIn(coroutineScope, started = SharingStarted.WhileSubscribed(5000))
            .launchIn(coroutineScope)
    }

    open operator fun unaryPlus() {
        callLogic()
    }

    private fun callLogic() {
        coroutineScope.launch {
            _itemFlow.emit(NetworkResult.Loading)
        }
    }
}