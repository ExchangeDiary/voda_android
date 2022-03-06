package com.example.domain.repo

import com.example.domain.entity.DataResult

interface TestRepository {
    suspend fun getTest(): DataResult<String>
}