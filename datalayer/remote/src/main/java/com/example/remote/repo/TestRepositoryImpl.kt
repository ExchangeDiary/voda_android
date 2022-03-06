package com.example.remote.repo

import com.example.domain.entity.DataResult
import com.example.domain.repo.TestRepository
import com.example.remote.ServiceBuilder
import retrofit2.Response
import javax.inject.Inject

class TestRepositoryImpl @Inject constructor() : TestRepository {
    override suspend fun getTest(): DataResult<String> {
        val response: Response<String> = ServiceBuilder.testApiService.getTest()

        return if (!response.isSuccessful || response.body() == null) {
            DataResult.Error(
                response.code().toString(),
                response.message() ?: "getTest Error"
            )
        } else
            DataResult.Success(response.body()!!)
    }
}
