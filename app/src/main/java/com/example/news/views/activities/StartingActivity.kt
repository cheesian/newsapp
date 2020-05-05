package com.example.news.views.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.news.R
import com.example.news.databinding.StartingActivityBinding
import com.example.news.utils.FullScreen.setFullScreen


/**
Created by ian
 */

class StartingActivity: AppCompatActivity() {

    lateinit var binding: StartingActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setFullScreen(this)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_starting)

    }

}