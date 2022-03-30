package com.example.voda_android

import android.app.Application
import com.example.domain.usecase.SetStringPreferencesUseCase
import com.kakao.sdk.common.KakaoSdk
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltAndroidApp
class VodaApplication : Application() {
    @Inject
    lateinit var setStringPreferencesOnceUseCase: SetStringPreferencesUseCase

    override fun onCreate() {
        super.onCreate()
        KakaoSdk.init(this, KAKAO_NATIVE_KEY)

        runBlocking {
            // TODO sangeun : access token 가져오는 것으로 변경.
            setStringPreferencesOnceUseCase("ACCESS_TOKEN", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdXRoX3R5cGUiOiJrYWthbyIsImlkIjo4LCJlbWFpbCI6IkBAIiwibmFtZSI6IuyDneydgCIsImV4cCI6MTY0ODY1OTYzNywiaWF0IjoxNjQ4NjM0NDM3LCJpc3MiOiJleGNoYW5nZS1kaWFyeSJ9.1VMwHFL_KeV6xpSDptMP-u9g7JGvb05utyBQvI27z38")
        }
    }

    companion object{
        const val KAKAO_NATIVE_KEY = "fe76dca000b0db0f426568cc69064d11"
    }
}