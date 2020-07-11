package com.programiqsolutions.news.views.fragments.feedback

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.programiqsolutions.news.databinding.FeedbackBinding

class Feedback: Fragment() {

    lateinit var binding: FeedbackBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FeedbackBinding.inflate(inflater)
        return binding.root
    }
}