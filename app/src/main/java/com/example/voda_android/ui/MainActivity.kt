package com.example.voda_android.ui

import android.os.Bundle
import com.example.voda_android.R
import com.example.voda_android.ui.base.BaseActivity

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}