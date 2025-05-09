package com.smartplant.feature_init.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.smartplant.feature_init.databinding.ActivityInitBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlin.getValue
import com.smartplant.core_android.R as CoreR

@AndroidEntryPoint
class InitActivity : AppCompatActivity() {
    private val viewModel: InitViewModel by viewModels()
    private lateinit var binding: ActivityInitBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityInitBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupButtons()

        subscribeForState()
        if (savedInstanceState == null) viewModel.startInitialization()
    }

    private fun subscribeForState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    if (state !is InitViewModel.UIState.Loading) setLoading(false)

                    when (state) {
                        is InitViewModel.UIState.Loading -> setLoading(true)
                        is InitViewModel.UIState.ServerUnavailable -> setError(CoreR.string.serverUnavailable)
                        is InitViewModel.UIState.UserUpdateSuccess -> navigateToMain()
                        is InitViewModel.UIState.NotAuthenticated -> if (state.isNewUser) navigateToStart() else navigateToAuth(getStringByStatusCode(state.statusCode))
                        is InitViewModel.UIState.Idle -> {}
                    }
                }
            }
        }
    }

    private fun setError(@StringRes errorStringResId: Int?) {
        if (errorStringResId == null) {
            binding.errorContainer.isVisible = false
            return
        }

        binding.errorContainer.isVisible = true
        binding.errorMessage.text = getString(errorStringResId)
    }

    private fun setLoading(loading: Boolean) {
        binding.spinner.isVisible = loading
    }

    @StringRes
    private fun getStringByStatusCode(statusCode: Int): Int {
        return when (statusCode) {  // TODO: add more statuses
            2000 -> CoreR.string.internalServerError
            2003 -> CoreR.string.internalServerError
            2004 -> CoreR.string.serverUnavailable
            -1 -> CoreR.string.internalServerError
            else -> CoreR.string.unknownError
        }
    }

    private fun navigateToAuth(@StringRes stringResId: Int?) {
        if (stringResId != null) showToast(stringResId)
        startActivity(Intent().setClassName(this, "com.smartplant.feature_auth.ui.AuthActivity"))
        finish()
    }

    private fun navigateToStart() {
        startActivity(Intent().setClassName(this, "com.smartplant.feature_start.ui.StartActivity"))
        finish()
    }

    private fun navigateToMain() {
        startActivity(Intent().setClassName(this, "com.smartplant.feature_main.ui.MainActivity"))
        finish()
    }

    private fun showToast(@StringRes stringResId: Int) {
        Toast.makeText(this, getString(stringResId), Toast.LENGTH_LONG).show()
    }

    private fun setupButtons() {
        binding.retryButton.setOnClickListener { _: View -> onRetryButtonClicked() }
    }

    private fun onRetryButtonClicked() {
        setError(null)
        viewModel.startInitialization()
    }
}
