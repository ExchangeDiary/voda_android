package com.example.voda_android.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.domain.entity.DataResult
import com.example.domain.usecase.TestUseCase
import com.example.voda_android.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getTestUseCase: TestUseCase,
) : BaseViewModel() {

    private val _testText = MutableStateFlow("")
    val testText: StateFlow<String> = _testText

    fun getTest() {
        viewModelScope.launch {
            when (val response = getTestUseCase()) {
                is DataResult.Success -> {
                    _testText.emit(response.data)
                }
                is DataResult.Error -> {
                    _testText.emit(response.message)
                }
            }
        }
    }
}