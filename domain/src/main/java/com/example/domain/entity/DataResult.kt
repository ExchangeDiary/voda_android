package com.example.domain.entity

sealed class DataResult<out T> {
    class Success<T>(val data: T) : DataResult<T>()
    class Error(val code: String, val message: String) : DataResult<Nothing>()
}
