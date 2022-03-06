package com.example.voda_android.di

import com.example.domain.repo.TestRepository
import com.example.remote.repo.TestRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@InstallIn(ViewModelComponent::class)
@Module
abstract class TestRepositoryModule {
    @Binds
    abstract fun provideTestRepository(impl: TestRepositoryImpl): TestRepository
}
