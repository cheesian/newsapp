package com.programiqsolutions.news.views.fragments.private

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.programiqsolutions.news.R
import com.programiqsolutions.news.data.Constants.CIPHER_WRAPPER
import com.programiqsolutions.news.data.Constants.PREFERENCE_FILE_NAME
import com.programiqsolutions.news.databinding.FragmentPrivateLoginBinding
import com.programiqsolutions.news.utils.BiometricPromptUtils
import com.programiqsolutions.news.utils.CipherTextWrapper
import com.programiqsolutions.news.utils.CryptographyManager
import com.programiqsolutions.news.utils.Notify.snackBar
import javax.crypto.Cipher


/**
Created by ian
 */

class PrivateLogin: Fragment() {

    lateinit var binding: FragmentPrivateLoginBinding
    private val cryptographyManager=  CryptographyManager()
    private val cipherWrapper get() = cryptographyManager.getCipherTextWrapperFromSharedPrefs(
        requireContext(),
        PREFERENCE_FILE_NAME,
        Context.MODE_PRIVATE,
        CIPHER_WRAPPER
    )
    val viewModel by viewModels<PrivateViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_private_login, container, false)
        setClickListeners()
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        checkForFingerprintAndToken()
    }

    private fun checkForFingerprintAndToken() {
        if ((cipherWrapper != null) && (!viewModel.getToken().isNullOrBlank())) {
            showDecryptionBiometricPrompt()
        }
    }

    private fun startPrivateFragment() {
        findNavController(this).navigate(R.id.private_fragment)
    }

    private fun setClickListeners() {
        binding.signInButton.setOnClickListener{
            if (viewModel.checkIfLoggedIn() && viewModel.checkIfPasswordIsCorrect()) {
                showEncryptionBiometricPrompt()
            }
        }
    }

//    Start of encryption functions
    private fun checkIfBiometricAuthenticationEnabled (): Boolean {
        val canAuthenticate = BiometricManager.from(requireContext()).canAuthenticate()
        return (canAuthenticate == BiometricManager.BIOMETRIC_SUCCESS)
    }

    private fun getCipherForEncryption(keyName: String, cryptoManager: CryptographyManager) :Cipher {
        return cryptoManager.getInitializedCipherForEncryption(keyName)
    }

    private fun encryptAndStorePrivateToken(authenticationResult: BiometricPrompt.AuthenticationResult) {
        authenticationResult.cryptoObject?.cipher?.apply {
            val encryptedTokenWrapper = cryptographyManager.encryptData(viewModel.getToken(), this)
            cryptographyManager.persistCipherTextWrapperToSharedPrefs(
                encryptedTokenWrapper,
                requireContext(),
                PREFERENCE_FILE_NAME,
                Context.MODE_PRIVATE,
                CIPHER_WRAPPER
            )
        }
    }

    private fun showEncryptionBiometricPrompt() {
        if (checkIfBiometricAuthenticationEnabled()) {
            val secretKeyName = requireContext().getString(R.string.type_feedback)
            val cipher = getCipherForEncryption(secretKeyName, cryptographyManager)
            val biometricPromptInformation = BiometricPromptUtils.createPromptInfo(activity as AppCompatActivity)
            val biometricPrompt = BiometricPromptUtils.createBiometricPrompt(activity as AppCompatActivity) {
                encryptAndStorePrivateToken(it)
                snackBar(view = binding.root, message = "Fingerprint recorded")
                startPrivateFragment()
            }
            biometricPrompt.authenticate(biometricPromptInformation, BiometricPrompt.CryptoObject(cipher))
        }
    }

//    Start of decryption functions
    private fun getCipherForDecryption(wrapper: CipherTextWrapper, keyName: String, cryptoManager: CryptographyManager): Cipher {
        return cryptoManager.getInitializedCipherForDecryption(keyName, wrapper.initializationVector)
    }

    private fun decryptAndGetPrivateToken(authenticationResult: BiometricPrompt.AuthenticationResult): String? {
        cipherWrapper?.let {wrapper->
            authenticationResult.cryptoObject?.cipher?.let {
                return cryptographyManager.decryptData(wrapper.cipherText, it)
            }
        }
        return null
    }

    private fun showDecryptionBiometricPrompt() {
        cipherWrapper?.let { textWrapper->
            if (checkIfBiometricAuthenticationEnabled()) {
                val secretKeyName = requireContext().getString(R.string.type_feedback)
                val biometricPromptInformation = BiometricPromptUtils.createPromptInfo(activity as AppCompatActivity)
                val cipher = getCipherForDecryption(textWrapper, secretKeyName, cryptographyManager)
                val biometricPrompt = BiometricPromptUtils.createBiometricPrompt(activity as AppCompatActivity) {
                    val token = decryptAndGetPrivateToken(it)
                    if (token != null) snackBar(view = binding.root, message = "Fingerprint verified")
                    else snackBar(view = binding.root, message = "Fingerprint verification failed")
                    startPrivateFragment()
                }
                biometricPrompt.authenticate(biometricPromptInformation, BiometricPrompt.CryptoObject(cipher))
            }
        }
    }
}