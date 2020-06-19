package com.example.news.views.fragments.start

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.news.R
import com.example.news.databinding.WelcomeBinding
import com.example.news.views.activities.start.StartingActivity


/**
Created by ian
 */

class Welcome: Fragment() {

    lateinit var binding: WelcomeBinding
    lateinit var loginButton: Button
    lateinit var signUpButton: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_welcome, container, false)

        loginButton = binding.signInButton
        loginButton.setOnClickListener {
            StartingActivity.INSTANCE!!.viewPager.currentItem = 2
        }

        signUpButton = binding.signUpButton
        signUpButton.setOnClickListener {
            StartingActivity.INSTANCE!!.viewPager.currentItem = 1
        }

        return binding.root
    }
}