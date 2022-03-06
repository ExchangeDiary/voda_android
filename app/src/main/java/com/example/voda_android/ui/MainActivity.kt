package com.example.voda_android.ui

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import androidx.lifecycle.asLiveData
import com.example.voda_android.R
import com.example.voda_android.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.observeOn

@AndroidEntryPoint
class MainActivity : BaseActivity() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setClicks()
        setObservers()
    }

    private fun setClicks() {
        findViewById<Button>(R.id.button).setOnClickListener {
            viewModel.getTest()
        }
    }

    private fun setObservers(){
        viewModel.testText.asLiveData().observe(this){
            findViewById<TextView>(R.id.textView).text = it
        }
    }
}
