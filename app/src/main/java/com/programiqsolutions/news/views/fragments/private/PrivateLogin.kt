package com.programiqsolutions.news.views.fragments.private

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.programiqsolutions.news.R
import com.programiqsolutions.news.databinding.FragmentPrivateLoginBinding
import com.programiqsolutions.news.utils.Notify.snackBar


/**
Created by ian
 */

class PrivateLogin: Fragment() {

    lateinit var binding: FragmentPrivateLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_private_login, container, false)
        setClickListeners()
        return binding.root
    }

    private fun proceed(view: View) {
        with(view) {
            findNavController().navigate(R.id.private_fragment)
            snackBar(view = view, message = "Clicked")
        }
    }

    private fun setClickListeners() {
        binding.signInButton.setOnClickListener(::proceed)
    }
}