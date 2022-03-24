package com.example.datastore.repo

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.datastore.DataStoreManager
import com.example.domain.repo.DataStoreRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DataStoreRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
) : DataStoreRepository {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "SOL_DATASTORE")
    private val datastoreManager = DataStoreManager(context.dataStore)

    override fun getDataStoreInt(key: String): Flow<Int?> {
        return datastoreManager.getDataStoreInt(key)
    }

    override fun getDataStoreBoolean(key: String): Flow<Boolean?> {
        return datastoreManager.getDataStoreBoolean(key)
    }

    override fun getDataStoreString(key: String): Flow<String?> {
        return datastoreManager.getDataStoreString(key)
    }

    override suspend fun getDataStoreIntOnce(key: String): Int? {
        return datastoreManager.getDataStoreIntOnce(key)
    }

    override suspend fun getDataStoreBooleanOnce(key: String): Boolean? {
        return datastoreManager.getDataStoreBooleanOnce(key)
    }

    override suspend fun getDataStoreStringOnce(key: String): String? {
        return datastoreManager.getDataStoreStringOnce(key)
    }

    override suspend fun setDataStoreInt(key: String, value: Int) {
        return datastoreManager.setDataStoreInt(key, value)
    }

    override suspend fun setDataStoreBoolean(key: String, value: Boolean) {
        return datastoreManager.setDataStoreBoolean(key, value)
    }

    override suspend fun setDataStoreString(key: String, value: String) {
        return datastoreManager.setDataStoreString(key, value)
    }
}