package com.codingwithmitch.hiltexperiment.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.codingwithmitch.hiltexperiment.R
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG: String = "AppDebug"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}



















