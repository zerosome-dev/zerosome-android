package com.zerosome.core

import android.util.Log
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

interface UIAction
interface UIIntent
interface UIState
interface UIEffect

abstract class BaseViewModel<A : UIAction, I : UIIntent, S : UIState, E : UIEffect>(
    initialState: S
) : ViewModel() {

    private val _uiState: MutableStateFlow<S> = MutableStateFlow(initialState)
    val uiState: StateFlow<S>
        get() = _uiState
    private val currentState
        get() = _uiState.value

    private val _uiAction: MutableSharedFlow<A> = MutableSharedFlow()
    private val uiAction = _uiAction

    private val _uiEffect: MutableSharedFlow<E> = MutableSharedFlow()
    val uiEffect: SharedFlow<E> = _uiEffect

    private val _isLoading= MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error: MutableSharedFlow<String?> = MutableSharedFlow()
    val error: SharedFlow<String?> = _error


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
        Log.d("CPRI", "STATED SET")
        viewModelScope.launch {
            _uiState.emit(transform(currentState))
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
                _isLoading.emit(true)
                flowOf(null)
            }
            is NetworkResult.Success -> {
                _isLoading.emit(false)
                flowOf(it.data)
            }
            is NetworkResult.Error -> {
                _isLoading.emit(false)
                _error.emit("\"${it.error.errorCode}\\n${it.error.code}}\")")
                flowOf(null)
            }
        }

    }

    fun clearError() {
        viewModelScope.launch {
            _error.emit("")
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