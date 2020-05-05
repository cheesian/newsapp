package com.example.news.views.fragments.start

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.news.R
import com.example.news.databinding.WelcomeBinding


/**
Created by ian
 */

class Welcome: Fragment() {

    lateinit var binding: WelcomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_welcome, container, false)
        return binding.root
    }
}