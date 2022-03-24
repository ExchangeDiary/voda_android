package com.example.voda_android.di

import com.example.datastore.repo.DataStoreRepositoryImpl
import com.example.domain.repo.DataStoreRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class DataStoreRepositoryModule {
    @Singleton
    @Binds
    abstract fun provideDataStoreRepository(impl: DataStoreRepositoryImpl): DataStoreRepository
}
