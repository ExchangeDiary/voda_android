package com.example.remote.api

import retrofit2.Response
import retrofit2.http.GET

interface TestApi {
    @GET("todos/1")
    suspend fun getTest(): Response<String>
}
