package com.example.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import kotlinx.coroutines.flow.*
import java.io.IOException

/*
*  datastore 에서 key 에 해당하는 value 를
*  1. Flow 로 반환 - getDataStore<TYPE>
*  2. value 값 하나만 반환 - getDataStore<TYPE>Once
*  3. 저장 - setDataStore<TYPE>
*/

class DataStoreManager(private val dataStore: DataStore<Preferences>) {

    suspend fun setDataStoreInt(key: String, value: Int) {
        setDataStore(intPreferencesKey(key), value)
    }

    suspend fun setDataStoreBoolean(key: String, value: Boolean) {
        setDataStore(booleanPreferencesKey(key), value)
    }

    suspend fun setDataStoreString(key: String, value: String) {
        setDataStore(stringPreferencesKey(key), value)
    }

    private suspend fun <T> setDataStore(key: Preferences.Key<T>, value: T) {
        dataStore.edit { preferences ->
            preferences[key] = value
        }
    }


    fun getDataStoreInt(key: String): Flow<Int?> =
        getDataStore(intPreferencesKey(key))


    fun getDataStoreBoolean(key: String): Flow<Boolean?> =
        getDataStore(booleanPreferencesKey(key))


    fun getDataStoreString(key: String): Flow<String?> =
        getDataStore(stringPreferencesKey(key))


    suspend fun getDataStoreIntOnce(key: String): Int? =
        getDataStoreInt(key).first()


    suspend fun getDataStoreBooleanOnce(key: String): Boolean? =
        getDataStoreBoolean(key).first()


    suspend fun getDataStoreStringOnce(key: String): String? =
        getDataStoreString(key).first()

    private fun <T> getDataStore(key: Preferences.Key<T>): Flow<T?> =
        dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { preferences ->
                preferences[key]
            }
}
