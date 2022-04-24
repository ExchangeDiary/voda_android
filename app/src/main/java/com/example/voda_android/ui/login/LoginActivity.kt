package com.example.voda_android.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.voda_android.R
import com.example.voda_android.databinding.ActivityLoginBinding
import com.example.voda_android.ui.base.BaseActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.kakao.sdk.auth.AuthApiClient
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.KakaoSdkError
import com.kakao.sdk.user.UserApiClient
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding>(R.layout.activity_login) {
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var requestGoogleLogin: ActivityResultLauncher<Intent>

    private val kakaoLoginCallback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        if (error != null) {
            Log.e(TAG, "로그인 실패", error)
        } else if (token != null) {
            Log.d(TAG, "로그인 성공 ${token.accessToken}")
            getKakaoUser()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setGoogleLogin()
        setClicks()
    }

    private fun setGoogleLogin() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)

        requestGoogleLogin = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { activityResult ->
            val task = GoogleSignIn.getSignedInAccountFromIntent(activityResult.data)
            handleGoogleLogin(task)
        }
    }

    private fun handleGoogleLogin(task: Task<GoogleSignInAccount>) {
        try {
            val account: GoogleSignInAccount = task.getResult(ApiException::class.java)
            getGoogleUser(account)
        } catch (e: ApiException) {
            Log.w(TAG, "signInResult:failed code=" + e.statusCode)
        }
    }

    private fun setClicks() {
        binding.goggle.setOnClickListener {
            val signInIntent = googleSignInClient.signInIntent
            requestGoogleLogin.launch(signInIntent)
        }

        binding.kakao.setOnClickListener {
            loginWithKakao()
        }
    }

    private fun loginWithKakao() {
        if (AuthApiClient.instance.hasToken()) {
            // accessToken 정보 제공(만료된 경우 갱신된 accessToken 제공)
            UserApiClient.instance.accessTokenInfo { accessToken, error ->
                if (error == null) {
                    Log.d(TAG, "accessTokenInfo 유효성 체크 성공, 회원번호 >> ${accessToken?.id}")
                    getKakaoUser()
                } else {
                    Log.d(TAG, "accessTokenInfo 유효성 체크 실패")

                    if (error is KakaoSdkError && error.isInvalidTokenError()) { // 로그인 필요
                        requestKakaoLogin()
                    } else { // 기타 에러
                        Log.e(TAG, "accessToken 기타에러", error)
                    }
                }
            }
        } else {
            requestKakaoLogin()
        }
    }

    private fun requestKakaoLogin() {
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(this)) {
            Log.d(TAG, "앱으로 로그인")
            UserApiClient.instance.loginWithKakaoTalk(this, callback = kakaoLoginCallback)
        } else {
            Log.d(TAG, "계정으로 로그인")
            UserApiClient.instance.loginWithKakaoAccount(this, callback = kakaoLoginCallback)
        }
    }

    private fun getKakaoUser() {
        // 사용자 정보 요청 (기본)
        UserApiClient.instance.me { user, error ->
            if (error != null) {
                Log.e(TAG, "사용자 정보 요청 실패", error)
            } else if (user != null) {
                Log.d(
                    TAG,
                    "사용자 정보 요청 성공" +
                            "\n회원번호: ${user.id}" +
                            "\n이메일: ${user.kakaoAccount?.email}" +
                            "\n닉네임: ${user.kakaoAccount?.profile?.nickname}" +
                            "\n프로필사진: ${user.kakaoAccount?.profile?.thumbnailImageUrl}"
                )
            }
        }
    }

    private fun getGoogleUser(user: GoogleSignInAccount) {
        // 사용자 정보 요청 (기본)
        Log.d(
            TAG,
            "사용자 정보 요청 성공" +
                    "\n회원번호: ${user.id}" +
                    "\n이메일: ${user.email}" +
                    "\n닉네임: ${user.displayName}" +
                    "\n프로필사진: ${user.photoUrl}"
        )
    }

    fun kakaoLogOut() { // 로그아웃
        UserApiClient.instance.logout { error ->
            if (error != null) Log.e(TAG, "로그아웃 실패. 그러 SDK에서 토큰 삭제됨", error)
            else Log.i(TAG, "로그아웃 성공. SDK에서 토큰 삭제됨")
        }
    }

    fun googleLogOut() {
        googleSignInClient.signOut()
            .addOnCompleteListener(this, OnCompleteListener<Void?> {
                // ...
            })
    }

    fun kakaoRevoke() { // 탈퇴
        UserApiClient.instance.unlink { error ->
            if (error != null) Log.e(TAG, "연결 끊기 실패", error)
            else Log.i(TAG, "연결 끊기 성공. SDK에서 토큰 삭제 됨")
        }
    }

    fun googleRevoke() {
        googleSignInClient.revokeAccess()
            .addOnCompleteListener(this, OnCompleteListener<Void?> {
                // ...
            })
    }

    companion object {
        private val TAG = LoginActivity::class.java.simpleName
    }
}