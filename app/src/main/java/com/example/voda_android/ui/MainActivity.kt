package com.example.voda_android.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.asLiveData
import com.example.voda_android.R
import com.example.voda_android.databinding.ActivityMainBinding
import com.example.voda_android.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setClicks()
        setObservers()
    }

    private fun setClicks() {
        binding.button.setOnClickListener {
            viewModel.getStringPreferences()
        }

        binding.editButton.setOnClickListener {
            viewModel.setStringPreferences(binding.editText.text.toString())
        }
    }

    private fun setObservers() {
        viewModel.stringPreference.asLiveData().observe(this) {
            binding.textView.text = it
        }
    }
}
