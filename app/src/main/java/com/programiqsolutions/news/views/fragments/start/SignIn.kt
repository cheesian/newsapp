package com.programiqsolutions.news.views.fragments.start

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.programiqsolutions.news.NewsApp
import com.programiqsolutions.news.R
import com.programiqsolutions.news.VMFactory
import com.programiqsolutions.news.data.Constants.PASSWORD_LENGTH
import com.programiqsolutions.news.data.request.signIn.SignInRequest
import com.programiqsolutions.news.databinding.SignInBinding
import com.programiqsolutions.news.utils.Notify.snackBar
import com.programiqsolutions.news.utils.Validation.checkEmailValidity
import com.programiqsolutions.news.utils.Validation.checkPasswordValidity
import com.programiqsolutions.news.views.fragments.start.viewModels.SignInViewModel
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import javax.inject.Inject


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
    lateinit var progressBar: ProgressBar
    @Inject lateinit var factory: VMFactory
    lateinit var viewModel: SignInViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (requireContext().applicationContext as NewsApp).applicationComponent.inject(this)

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_in, container, false)

        viewModel = ViewModelProvider(this, factory).get(SignInViewModel::class.java)
        email = binding.emailText
        emailLayout = binding.emailLayout
        pass = binding.pass2Text
        passLayout = binding.pass2Layout
        progressBar = binding.progress

        viewModel.generalResponse.observe(viewLifecycleOwner, Observer {
            viewModel.consumeResponse(it)
        })
        viewModel.progressBarVisibility.observe(viewLifecycleOwner, Observer {
            progressBar.visibility = it
        })
        viewModel.message.observe(viewLifecycleOwner, Observer {
            snackBar(
                view = binding.root,
                message = it
            )
        })

        signInButton = binding.signInButton
        signInButton.setOnClickListener {
            signIn()
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

    private fun signIn() {
        if (checkFormValidity()) {
            viewModel.signIn(
                SignInRequest(
                email = email.text.toString(),
                password = pass.text.toString()
            )
            )
        }
    }

}