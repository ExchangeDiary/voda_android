package com.example.domain.repo

import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {
    fun getDataStoreInt(key: String): Flow<Int?>
    fun getDataStoreBoolean(key: String): Flow<Boolean?>
    fun getDataStoreString(key: String): Flow<String?>

    suspend fun getDataStoreIntOnce(key: String): Int?
    suspend fun getDataStoreBooleanOnce(key: String): Boolean?
    suspend fun getDataStoreStringOnce(key: String): String?

    suspend fun setDataStoreInt(key: String, value: Int)
    suspend fun setDataStoreBoolean(key: String, value: Boolean)
    suspend fun setDataStoreString(key: String, value: String)
}