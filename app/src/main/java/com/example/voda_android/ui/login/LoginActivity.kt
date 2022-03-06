package com.example.voda_android.ui.login

import android.os.Bundle
import androidx.activity.viewModels
import com.example.voda_android.R
import com.example.voda_android.databinding.ActivityLoginBinding
import com.example.voda_android.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding>(R.layout.activity_login) {
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setClicks()
    }

    private fun setClicks() {
        binding.goggle.setOnClickListener {
        }

        binding.kakao.setOnClickListener {
        }
    }
}