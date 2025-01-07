package com.zerosome.domain

import android.util.Log
import com.zerosome.core.constants.ClientError
import com.zerosome.core.constants.ClientExceptions
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
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
        _itemFlow.update { NetworkResult.Error(ClientExceptions(ClientError.NETWORK_UNVALIDATED_INVALID)) }
    }

    val coroutineScope = CoroutineScope(Dispatchers.IO + exceptionHandler)


    private val _itemFlow = MutableStateFlow<NetworkResult<T>>(NetworkResult.Loading)

    open operator fun invoke() = _itemFlow.onEach {
        Log.d("CPRI", "ITEM INIT ${this::class} && $it")
    }

    init {
        _itemFlow.launchIn(scope = coroutineScope)
        coroutineScope.launch {
            callLogic()
        }
    }

    open suspend fun innerLogic(): NetworkResult<T> {
        throw NotImplementedError()
    }

    open fun refresh() {
        throw NotImplementedError()
    }
    open operator fun unaryPlus() {
        throw NotImplementedError()
    }

    protected suspend fun callLogic() {
        _itemFlow.emit(NetworkResult.Loading)
        val response = innerLogic()
        Log.d("CPRI", "RESPONSE")
        _itemFlow.emit(response)
    }
}