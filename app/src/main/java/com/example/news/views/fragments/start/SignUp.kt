package com.example.news.views.fragments.start

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.news.NewsApp
import com.example.news.R
import com.example.news.VMFactory
import com.example.news.data.Constants.PASSWORD_LENGTH
import com.example.news.databinding.SignUpBinding
import com.example.news.utils.Validation.checkEmailValidity
import com.example.news.utils.Validation.checkPasswordValidity
import com.example.news.utils.Validation.hasOnlyLettersAndSpaces
import com.example.news.views.fragments.start.viewModels.SignUpViewModel
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import javax.inject.Inject


/**
Created by ian
 */

class SignUp: Fragment() {

    lateinit var binding: SignUpBinding
    lateinit var name: TextInputEditText
    lateinit var nameLayout: TextInputLayout
    lateinit var email: TextInputEditText
    lateinit var emailLayout: TextInputLayout
    lateinit var pass: TextInputEditText
    lateinit var passLayout: TextInputLayout
    lateinit var pass2: TextInputEditText
    lateinit var pass2Layout: TextInputLayout
    lateinit var signUpButton: Button
    @Inject lateinit var factory: VMFactory
    lateinit var viewModel: SignUpViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (context!!.applicationContext as NewsApp).applicationComponent.inject(this)

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_up, container, false)

        viewModel = ViewModelProvider(this, factory).get(SignUpViewModel::class.java)
        name = binding.nameText
        nameLayout = binding.nameLayout
        email = binding.emailText
        emailLayout = binding.emailLayout
        pass = binding.passText
        passLayout = binding.passLayout
        pass2 = binding.pass2Text
        pass2Layout = binding.pass2Layout

        signUpButton = binding.signUpButton
        signUpButton.setOnClickListener {
            checkFormValidity()
        }
        return binding.root
    }

    private fun checkFormValidity(): Boolean {
        val isValidNames = hasOnlyLettersAndSpaces(name.text.toString())
        nameLayout.error = if (!isValidNames) "Invalid Names" else null
        val isValidEmail = checkEmailValidity(email.text.toString())
        emailLayout.error = if (!isValidEmail) "Invalid Email" else null
        val isValidPassword = checkPasswordValidity(pass.text.toString(), PASSWORD_LENGTH)
        passLayout.error = if (!isValidPassword) "Use at least $PASSWORD_LENGTH characters" else null
        val passMatch = pass.text.toString() == pass2.text.toString()
        pass2Layout.error = if (!passMatch) "Passwords do not match" else null
        return isValidEmail && isValidPassword
    }
}