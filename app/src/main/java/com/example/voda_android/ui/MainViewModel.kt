package com.example.voda_android.ui

import androidx.lifecycle.viewModelScope
import com.example.domain.entity.DataResult
import com.example.domain.usecase.GetStringPreferencesOnceUseCase
import com.example.domain.usecase.GetStringPreferencesUseCase
import com.example.domain.usecase.SetStringPreferencesUseCase
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
    private val getStringPreferencesUseCase: GetStringPreferencesUseCase,
    private val getStringPreferencesOnceUseCase: GetStringPreferencesOnceUseCase,
    private val setStringPreferencesUseCase: SetStringPreferencesUseCase,
) : BaseViewModel() {

    private val _testText = MutableStateFlow("")
    val testText: StateFlow<String> = _testText

    private val _stringPreference = MutableStateFlow("")
    val stringPreference: StateFlow<String> = _stringPreference

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

    fun getStringPreferences() {
        viewModelScope.launch {
            // 1. flow 형태로 받는 경우
//            getStringPreferencesOnceUseCase(KEY).collect {
//                _stringPreference.value = it ?: ""
//            }

            // 2. 한번만 String 형태로 받는 경우.
            _stringPreference.value = getStringPreferencesOnceUseCase(KEY) ?: ""
        }
    }

    fun setStringPreferences(str: String) {
        viewModelScope.launch {
            // 저장하는 방법은 한개로 동일.
            setStringPreferencesUseCase(KEY, str)
        }
    }

    companion object {
        const val KEY = "KEY"
    }
}