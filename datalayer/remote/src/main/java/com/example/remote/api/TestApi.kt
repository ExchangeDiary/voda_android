package com.example.remote.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Url

interface TestApi {
//    @Headers("Authorization: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdXRoX3R5cGUiOiJrYWthbyIsImlkIjo0LCJlbWFpbCI6IkAiLCJuYW1lIjoi7IOd7Jq0IiwiZXhwIjoxNjQ4MTUwMzI1LCJpYXQiOjE2NDgxMjUxMjUsImlzcyI6ImV4Y2hhbmdlLWRpYXJ5In0.QyjenH7ki-7_bX6D0kTPd0iAEcxQUylPwkkLCQCfuTQ")
    @GET("rooms")
    suspend fun getTest(): Response<String>
}
