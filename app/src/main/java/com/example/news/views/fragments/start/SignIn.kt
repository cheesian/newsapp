package com.example.news.views.fragments.start

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.news.R
import com.example.news.data.Constants.PASSWORD_LENGTH
import com.example.news.databinding.SignInBinding
import com.example.news.utils.Validation.checkEmailValidity
import com.example.news.utils.Validation.checkPasswordValidity
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout


/**
Created by ian
 */

class SignIn: Fragment() {

    lateinit var binding: SignInBinding
    lateinit var email: TextInputEditText
    lateinit var emailLayout: TextInputLayout
    lateinit var pass: TextInputEditText
    lateinit var passLayout: TextInputLayout
    lateinit var signInButton: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_in, container, false)

        email = binding.emailText
        emailLayout = binding.emailLayout
        pass = binding.pass2Text
        passLayout = binding.pass2Layout

        signInButton = binding.signInButton
        signInButton.setOnClickListener {
            checkFormValidity()
        }

        return binding.root
    }

    private fun checkFormValidity(): Boolean {
        val isValidEmail = checkEmailValidity(email.text.toString())
        emailLayout.error = if (!isValidEmail) "Invalid Email" else null
        val isValidPassword = checkPasswordValidity(pass.text.toString(), PASSWORD_LENGTH)
        passLayout.error = if (!isValidPassword) "Use at least $PASSWORD_LENGTH characters" else null
        return isValidEmail && isValidPassword
    }

}