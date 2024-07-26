package com.zerosome.core

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.traceEventStart
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zerosome.network.NetworkResult
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlin.reflect.KProperty

/**
 * User의 직접적인 액션으로만 바꿀 수 있습니다.
 */
interface UIAction

/**
 * 세부적인 VM의 흐름을 관리합니다.
 */
interface UIIntent

/**
 * 유저에게 직접적으로 보여지는 UI를 담당합니다.
 */
interface UIState

/**
 * 유저가 보지 못하는 UX 적인 부분을 담당합니다. ( ex. Navigation, Dialog, ..etc )
 */
interface UIEffect

abstract class BaseViewModel<A : UIAction, I : UIIntent, S : UIState, E : UIEffect>(
    initialState: S
) : ViewModel() {

    private var _uiState by mutableStateOf(initialState)
    val uiState
        get() = _uiState

    private val _uiAction: MutableSharedFlow<A> = MutableSharedFlow()
    private val uiAction = _uiAction

    private val _uiEffect: MutableSharedFlow<E> = MutableSharedFlow()
    val uiEffect: SharedFlow<E> = _uiEffect

    private var _isLoading by mutableStateOf(false)
    val isLoading
        get() = _isLoading

    private var _error by mutableStateOf("")
    val error = _error


    init {
        viewModelScope.launch {
            uiAction.collect {
                collectIntent(actionPredicate(it))
            }
        }
    }

    abstract fun actionPredicate(action: A): I

    abstract fun collectIntent(intent: I)

    protected fun setState(transform: S.() -> S) {
        _uiState = transform(uiState)
    }

    protected fun withState(block: suspend S.() -> Unit) {
        viewModelScope.launch {
            block(uiState)
        }
    }

    protected fun setEffect(transform: () -> E) {
        viewModelScope.launch {
            _uiEffect.emit(transform())
        }
    }

    protected fun <T> Flow<NetworkResult<T>>.mapMerge(): Flow<T?> = flatMapConcat {
        when (it) {
            is NetworkResult.Loading -> {
                _isLoading = true
                flowOf(null)
            }
            is NetworkResult.Success -> {
                _isLoading = false
                flowOf(it.data)
            }
            is NetworkResult.Error -> {
                _isLoading = false
                _error = "\"${it.error.errorCode}\\n${it.error.code}}\")"
                flowOf(null)
            }
        }

    }

    fun clearError() {
        viewModelScope.launch {
            _error = ""
        }
    }

    fun setAction(action: A) {
        Log.d("CPRI", "HANDLE ACTION $action")
        viewModelScope.launch {
            _uiAction.emit(action)
        }
    }
    protected fun setIntent(intent: I) = viewModelScope.launch {
        collectIntent(intent)
    }
}