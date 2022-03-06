package com.example.remote

import com.example.remote.api.TestApi
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

object ServiceBuilder {
//    private const val baseUrl = "https://exchange-diary-b4mzhzbzcq-du.a.run.app/v1/"
    private const val baseUrl = "https://jsonplaceholder.typicode.com/"

    val testApiService by lazy { buildService<TestApi>() }

    private val vodaJson = Json {
        ignoreUnknownKeys = true // data class에 없는 키가 들어오면 에러 -> 방지
        encodeDefaults = true // 요청(dataclass->json)시 data class의 기본값을 적용시켜줌. 안그러면 안들어감.
        isLenient = true // string 밸류에 ""가 안들어오는 것과 같은 것을 유연하게 처리해줌.
        coerceInputValues = true // 디코딩 시(json -> data class) 강력한 type check를 느슨하게 해줌. 예를들면, json에서 null로 왔는데 해당 프로퍼티는 non-null이다. 이 때 에러를 뱉지않음. 이 프로퍼티에 디폴트 밸류를 설정하면 그 값이 모델에 반영되게 함.
    }

    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(vodaJson.asConverterFactory(MediaType.get("application/json")))
            .build()
    }

    inline fun <reified T> buildService(): T {
        return retrofit.create(T::class.java)
    }
}
