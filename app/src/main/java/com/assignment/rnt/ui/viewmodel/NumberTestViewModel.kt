package com.assignment.rnt.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.assignment.rnt.R
import com.assignment.rnt.RandomNumberApplication
import com.assignment.rnt.data.NumberDataSource
import com.assignment.rnt.model.Result
import com.assignment.rnt.util.InputUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

/**
 * ViewModel for "Number Test" screen.
 */
@HiltViewModel
class NumberTestViewModel @Inject constructor(
    private val numberDataSource: NumberDataSource,
    application: Application
) : AndroidViewModel(application) {

    private val _numberUiState = MutableStateFlow<RemoteNumberUiState>(RemoteNumberUiState.Empty)
    private val _inputUiState = MutableStateFlow<LocalNumberUiState>(LocalNumberUiState.Normal())

    val numberUiState: StateFlow<RemoteNumberUiState> = _numberUiState
    val inputUiState: StateFlow<LocalNumberUiState> = _inputUiState

    init {
        fetchNumber()
    }

    fun fetchNumber(forceRefresh: Boolean = false) {
        Timber.d("fetchNumber forceRefresh: $forceRefresh")
        viewModelScope.launch {
            numberDataSource.fetchNumber(forceRefresh = forceRefresh).collect { result ->
                Timber.d("result: $result")
                _numberUiState.value = when (result.status) {
                    Result.Status.LOADING -> {
                        if (numberUiState.value is RemoteNumberUiState.Loaded) {
                            numberUiState.value
                        } else {
                            RemoteNumberUiState.Loading
                        }
                    }
                    Result.Status.SUCCESS -> {
                        result.data?.let {
                            RemoteNumberUiState.Loaded(it)
                        } ?: RemoteNumberUiState.Empty
                    }
                    Result.Status.ERROR -> RemoteNumberUiState.Error(result.message ?: "Error")
                }
            }
        }
    }

    fun validateInput(inputData: String) {
        Timber.d("validateInput input: $inputData")
        val input = inputData.trim()
        val isValidInput = InputUtils.validateNumberInput(input)
        _inputUiState.value = if (isValidInput) {
            LocalNumberUiState.Normal(input)
        } else {
            LocalNumberUiState.Error(
                input,
                getApplication<RandomNumberApplication>()
                    .getString(R.string.number_test_error_invalid_number)
            )
        }
    }

    fun compare() {
        Timber.d("compare")
        //todo compare show result
    }

}

sealed class RemoteNumberUiState {
    object Empty : RemoteNumberUiState()
    object Loading : RemoteNumberUiState()
    class Loaded(val number: Int) : RemoteNumberUiState()
    class Error(val message: String) : RemoteNumberUiState()
}

sealed class LocalNumberUiState(val numberString: String) {

    val number: Int?
        get() {
            return numberString.toIntOrNull()
        }

    class Normal(numberString: String = "") : LocalNumberUiState(numberString)
    class Error(
        numberString: String = "",
        val error: String? = null,
    ) : LocalNumberUiState(numberString)
}