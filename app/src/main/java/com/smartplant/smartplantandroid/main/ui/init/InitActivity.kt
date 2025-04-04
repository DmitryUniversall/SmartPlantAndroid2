package com.smartplant.smartplantandroid.main.ui.init

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
import com.smartplant.smartplantandroid.R
import com.smartplant.smartplantandroid.core.logs.AppLogger
import com.smartplant.smartplantandroid.databinding.ActivityInitBinding
import com.smartplant.smartplantandroid.main.ui.auth.AuthActivity
import com.smartplant.smartplantandroid.main.ui.main.MainActivity
import com.smartplant.smartplantandroid.main.ui.start.StartActivity
import kotlinx.coroutines.launch


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
                    AppLogger.info("STATE: $state")

                    when (state) {
                        is InitViewModel.UIState.Loading -> setLoading(true)
                        is InitViewModel.UIState.ServerUnavailable -> setError(R.string.serverUnavailable)
                        is InitViewModel.UIState.UserUpdateSuccess -> navigateToMain()
                        is InitViewModel.UIState.NotAuthenticated -> if (state.isNewUser) navigateToStart() else navigateToAuth(getStringByStatusCode(state.statusCode))
                        is InitViewModel.UIState.Idle -> {}
                    }
                }
            }
        }
    }

    private fun setLoading(loading: Boolean) {
        binding.spinner.isVisible = loading
    }

    @StringRes
    private fun getStringByStatusCode(statusCode: Int): Int {
        return when (statusCode) {  // TODO: add more statuses
            2000 -> R.string.internalServerError
            2003 -> R.string.internalServerError
            2004 -> R.string.serverUnavailable
            -1 -> R.string.internalServerError
            else -> R.string.unknownError
        }
    }

    private fun setError(@StringRes errorStringResId: Int?) {
        if (errorStringResId == null) {
            binding.errorContainer.isVisible = false
            return
        }

        AppLogger.error("ERROR")

        binding.errorContainer.isVisible = true
        binding.errorMessage.text = getString(errorStringResId)
    }

    private fun navigateToAuth(@StringRes stringResId: Int?) {
        if (stringResId != null) showToast(stringResId)
        startActivity(Intent(this, AuthActivity::class.java))
        finish()
    }

    private fun navigateToStart() {
        startActivity(Intent(this, StartActivity::class.java))
        finish()
    }

    private fun navigateToMain() {
        startActivity(Intent(this, MainActivity::class.java))
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
