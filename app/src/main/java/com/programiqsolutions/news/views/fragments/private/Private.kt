package com.programiqsolutions.news.views.fragments.private

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.programiqsolutions.news.R
import com.programiqsolutions.news.databinding.FragmentPrivateBinding


/**
Created by ian
 */

class Private: Fragment() {

    lateinit var binding: FragmentPrivateBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_private, container, false)
        return binding.root
    }
}