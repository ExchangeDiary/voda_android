package com.example.domain.usecase

import com.example.domain.entity.DataResult
import com.example.domain.repo.TestRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TestUseCase @Inject constructor(
    private val repository: TestRepository
) {
    suspend operator fun invoke(): DataResult<String> = repository.getTest()
}
