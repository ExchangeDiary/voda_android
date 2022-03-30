package com.example.remote

import android.util.Log
import com.example.domain.usecase.GetStringPreferencesOnceUseCase
import com.example.remote.api.TestApi
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Inject
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TokenInterceptorModule {
    const val baseUrl = "https://exchange-diary-b4mzhzbzcq-du.a.run.app/v1/"

    private val vodaJson = Json {
        ignoreUnknownKeys = true // data class에 없는 키가 들어오면 에러 -> 방지
        encodeDefaults = true // 요청(dataclass->json)시 data class의 기본값을 적용시켜줌. 안그러면 안들어감.
        isLenient = true // string 밸류에 ""가 안들어오는 것과 같은 것을 유연하게 처리해줌.
        coerceInputValues =
            true // 디코딩 시(json -> data class) 강력한 type check를 느슨하게 해줌. 예를들면, json에서 null로 왔는데 해당 프로퍼티는 non-null이다. 이 때 에러를 뱉지않음. 이 프로퍼티에 디폴트 밸류를 설정하면 그 값이 모델에 반영되게 함.
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(tokenInterceptor: TokenInterceptor): OkHttpClient =
        OkHttpClient.Builder().addInterceptor(tokenInterceptor).build()

    @Singleton
    @Provides
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(vodaJson.asConverterFactory(MediaType.get("application/json")))
            .build()
    }

    @Provides
    @Singleton
    fun provideTestApiService(retrofit: Retrofit): TestApi {
        return retrofit.create(TestApi::class.java)
    }
}

class TokenInterceptor @Inject constructor() : Interceptor {
    @Inject
    lateinit var getStringPreferencesOnceUseCase: GetStringPreferencesOnceUseCase

    override fun intercept(chain: Interceptor.Chain): Response {
        var accessToken: String = ""
        runBlocking {
            accessToken = getStringPreferencesOnceUseCase("ACCESS_TOKEN") ?: ""
        }
        Log.d("sangeun", "token: $accessToken")
        val request = chain.request().newBuilder().addHeader("Authorization", accessToken).build()
        val response: Response = chain.proceed(request)

        // 2. 위 Response에서 응답 json을 꺼내 서버 응답 코드가 토큰 만료 에러 코드인지 확인한다.
        if (!response.isSuccessful) { // 응답 토큰이 만료되었는지 체
            // 4. MobileTokenRepository 로부터 갱신된 토큰(Refreshed Token)을 가져온다.
//            val refreshedToken = mobileTokenRepository.getRefreshedToken()

            // 5. chain의 Request 객체를 복사해 재발급한 토큰을 Header에 넣고 요청을 보낸다.
//            val refreshedRequest = chain.request().putTokenHeader(refreshedToken)
//            return chain.proceed(refreshedRequest)
        }

        // 3. 토큰 만료에러가 아니면 응답을 그대로 반환 한다.
        return response
    }
}
