package com.zerosome.feat.core

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zerosome.core.analytics.AnalyticsLogger
import com.zerosome.core.constants.ClientExceptions
import com.zerosome.domain.NetworkResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.launch

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

open class BaseViewModel<A : UIAction, I : UIIntent, S : UIState, E : UIEffect>(
    initialState: S
) : ViewModel() {

    protected val analyticsLogger = AnalyticsLogger()

    var uiState by mutableStateOf(initialState)
        protected set


    private val _uiAction: MutableSharedFlow<A> = MutableSharedFlow()
    private val uiAction = _uiAction.onEach {
        _uiIntent.emit(actionPredicate(it))
    }.launchIn(viewModelScope)

    private val _uiIntent: MutableSharedFlow<I> = MutableSharedFlow()
    private val uiIntent = _uiIntent.onEach {
        isLoading = true
        collectIntent(it)
    }.launchIn(viewModelScope)

    private val _uiEffect: MutableSharedFlow<E?> = MutableSharedFlow()
    val uiEffect: SharedFlow<E?> = _uiEffect

    var isLoading by mutableStateOf(false)
        private set

    private var _error by mutableStateOf("")
    val error = _error

    open suspend fun actionPredicate(action: A): I = _uiIntent.single()

    open suspend fun collectIntent(intent: I) {}

    protected fun setState(transform: S.() -> S) {
        isLoading = false
        uiState = transform(uiState)
    }

    protected fun setEffect(transform: () -> E) {
        isLoading = false
        viewModelScope.launch {
            _uiEffect.emit(transform())
        }
    }

    protected fun init(vararg flows: Flow<NetworkResult<*>>) {
        combine(*flows) { flowList ->
            isLoading = flowList.any { it is NetworkResult.Loading }
        }
        flows.forEach {
            it.launchIn(viewModelScope)
        }
    }

    protected fun <T> Flow<NetworkResult<T>>.mapMerge(
        onFailure: (ClientExceptions) -> Unit = {},
        onSuccess: (T) -> Unit,
    ): Flow<NetworkResult<T>> = onEach {
        Log.d("CPRI", "VIEWMODEL RESULT $it")
        when (it) {
            is NetworkResult.Loading -> {
            }
            is NetworkResult.Success -> {
                onSuccess(it.data)
            }
            is NetworkResult.Error -> {
                _error = "\"${it.error.message}"
                onFailure(it.error)
            }
        }
    }

    fun clearError() {
        viewModelScope.launch {
            _error = ""
        }
    }

    fun clearEffect() {
        viewModelScope.launch {
            _uiEffect.emit(null)
        }
    }

    fun setAction(action: A) {
        viewModelScope.launch {
            _uiAction.emit(action)
        }
    }
}