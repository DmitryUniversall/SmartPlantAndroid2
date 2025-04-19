package com.smartplant.smartplantandroid.main.ui.activities.auth

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.smartplant.smartplantandroid.R
import com.smartplant.smartplantandroid.databinding.ActivityAuthBinding
import com.smartplant.smartplantandroid.main.ui.activities.auth.fragments.AuthLoginFragment
import com.smartplant.smartplantandroid.main.ui.activities.auth.fragments.AuthRegisterFragment
import com.smartplant.smartplantandroid.main.ui.global.makeOutlinedPrimary
import com.smartplant.smartplantandroid.main.ui.global.makePrimary
import kotlinx.coroutines.launch

class AuthActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAuthBinding
    private val viewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        subscribe()
        setupNav()
        setupPager()
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

    private fun subscribe() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    when (state) {
                        is AuthViewModel.AuthPageState.Login -> setLoginPage()
                        is AuthViewModel.AuthPageState.Register -> setRegisterPage()
                    }
                }
            }
        }
    }

    private fun setState(state: AuthViewModel.AuthPageState) {
        if (state == viewModel.state.value) return

        viewModel.setState(state)

        if (viewModel.state.value == AuthViewModel.AuthPageState.Login) binding.loginButton.makePrimary() else binding.loginButton.makeOutlinedPrimary()
        if (viewModel.state.value == AuthViewModel.AuthPageState.Register) binding.registerButton.makePrimary() else binding.registerButton.makeOutlinedPrimary()
        binding.submitButton.text = if (viewModel.state.value == AuthViewModel.AuthPageState.Login) getString(R.string.loginAction) else getString(R.string.registerAction)

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
}
