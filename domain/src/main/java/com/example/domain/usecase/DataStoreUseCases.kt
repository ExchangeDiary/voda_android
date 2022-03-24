package com.example.domain.usecase

import com.example.domain.repo.DataStoreRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBooleanPreferencesUseCase @Inject constructor(
    private val repository: DataStoreRepository
) {
    operator fun invoke(key: String): Flow<Boolean?> = repository.getDataStoreBoolean(key)
}

class GetIntPreferencesUseCase @Inject constructor(
    private val repository: DataStoreRepository
) {
    operator fun invoke(key: String): Flow<Int?> = repository.getDataStoreInt(key)
}

class GetStringPreferencesUseCase @Inject constructor(
    private val repository: DataStoreRepository
) {
    operator fun invoke(key: String): Flow<String?> = repository.getDataStoreString(key)
}

class GetBooleanPreferencesOnceUseCase @Inject constructor(
    private val repository: DataStoreRepository
) {
    suspend operator fun invoke(key: String): Boolean? = repository.getDataStoreBooleanOnce(key)
}

class GetIntPreferencesOnceUseCase @Inject constructor(
    private val repository: DataStoreRepository
) {
    suspend operator fun invoke(key: String): Int? = repository.getDataStoreIntOnce(key)
}

class GetStringPreferencesOnceUseCase @Inject constructor(
    private val repository: DataStoreRepository
) {
    suspend operator fun invoke(key: String): String? = repository.getDataStoreStringOnce(key)
}

class SetBooleanPreferencesUseCase @Inject constructor(
    private val repository: DataStoreRepository
) {
    suspend operator fun invoke(key: String, value: Boolean) = repository.setDataStoreBoolean(key, value)
}

class SetIntPreferencesUseCase @Inject constructor(
    private val repository: DataStoreRepository
) {
    suspend operator fun invoke(key: String, value: Int) = repository.setDataStoreInt(key, value)
}

class SetStringPreferencesUseCase @Inject constructor(
    private val repository: DataStoreRepository
) {
    suspend operator fun invoke(key: String, value: String) = repository.setDataStoreString(key, value)
}
