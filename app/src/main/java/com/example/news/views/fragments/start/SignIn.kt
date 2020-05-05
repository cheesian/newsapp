package com.example.news.views.fragments.start

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.news.R
import com.example.news.databinding.SignInBinding


/**
Created by ian
 */

class SignIn: Fragment() {

    lateinit var binding: SignInBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_in, container, false)
        return binding.root
    }

}