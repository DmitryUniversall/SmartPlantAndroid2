package com.smartplant.feature_auth.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.smartplant.core_android.ui.makeOutlinedPrimary
import com.smartplant.core_android.ui.makePrimary
import com.smartplant.feature_auth.R
import com.smartplant.feature_auth.databinding.ActivityAuthBinding
import com.smartplant.feature_auth.ui.fragments.AuthLoginFragment
import com.smartplant.feature_auth.ui.fragments.AuthRegisterFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import com.smartplant.core_android.R as CoreR

@AndroidEntryPoint
class AuthActivity : AppCompatActivity() {
    private val viewModel: AuthViewModel by viewModels()
    private lateinit var binding: ActivityAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        subscribeToPageState()
        subscribeToAuthState()
        setupNav()
        setupPager()
        setupSubmitButton()
    }

    private fun setState(state: AuthViewModel.AuthPageState) {
        viewModel.setPageState(state)

        if (state == AuthViewModel.AuthPageState.Login) binding.loginButton.makePrimary() else binding.loginButton.makeOutlinedPrimary()
        if (state == AuthViewModel.AuthPageState.Register) binding.registerButton.makePrimary() else binding.registerButton.makeOutlinedPrimary()
        binding.submitButton.text = if (state == AuthViewModel.AuthPageState.Login) getString(R.string.loginAction) else getString(R.string.registerAction)

        when (state) {
            is AuthViewModel.AuthPageState.Login -> binding.authFragmentPager.setCurrentItem(0, true)
            is AuthViewModel.AuthPageState.Register -> binding.authFragmentPager.setCurrentItem(1, true)
        }
    }

    private fun setLoginPage() {
        setState(AuthViewModel.AuthPageState.Login)
    }

    private fun setRegisterPage() {
        setState(AuthViewModel.AuthPageState.Register)
    }

    private fun navigateToMain() {
        startActivity(Intent().setClassName(this, "com.smartplant.feature_main.ui.MainActivity"))
        finish()
    }

    private fun showErrorMessage(@StringRes message: Int) {
        binding.errorMessage.isVisible = true
        binding.errorMessage.text = getString(message)
    }

    private fun hideErrorMessage() {
        binding.errorMessage.isVisible = false
        binding.errorMessage.text = ""
    }

    @StringRes
    private fun getMessageResForStatus(applicationStatusCode: Int): Int {
        return when (applicationStatusCode) {
            2001 -> R.string.userAlreadyExists
            3008 -> R.string.userNotFound
            3009 -> R.string.WrongAuthenticationCredentials
            else -> CoreR.string.internalServerError
        }
    }

    private fun setupNav() {
        binding.loginButton.setOnClickListener { setLoginPage() }
        binding.registerButton.setOnClickListener { setRegisterPage() }
    }

    private fun setupPager() {
        binding.authFragmentPager.apply {
            isUserInputEnabled = false
            adapter = object : FragmentStateAdapter(this@AuthActivity) {
                override fun getItemCount() = 2
                override fun createFragment(position: Int) = if (position == 0) AuthLoginFragment() else AuthRegisterFragment()
            }
        }
    }

    private fun setupSubmitButton() {
        binding.submitButton.setOnClickListener {
            viewModel.submit()
        }
    }

    private fun subscribeToPageState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.pageState.collect { state ->
                    when (state) {
                        is AuthViewModel.AuthPageState.Login -> setLoginPage()
                        is AuthViewModel.AuthPageState.Register -> setRegisterPage()
                    }
                }
            }
        }
    }

    private fun subscribeToAuthState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.authState.collect { event ->
                    if (event !is AuthViewModel.AuthEvent.Loading) onLoaded()

                    when (event) {
                        is AuthViewModel.AuthEvent.Loading -> onLoading()
                        is AuthViewModel.AuthEvent.Success -> onAuthSuccess()
                        is AuthViewModel.AuthEvent.Error -> onAuthError(event.applicationStatusCode)
                    }
                }
            }
        }
    }

    private fun onAuthSuccess() {
        navigateToMain()
    }

    private fun onAuthError(applicationStatusCode: Int) {
        showErrorMessage(getMessageResForStatus(applicationStatusCode))
    }

    private fun onLoading() {
        hideErrorMessage()
        binding.submitButton.isEnabled = false
    }

    private fun onLoaded() {
        binding.submitButton.isEnabled = true
    }
}
