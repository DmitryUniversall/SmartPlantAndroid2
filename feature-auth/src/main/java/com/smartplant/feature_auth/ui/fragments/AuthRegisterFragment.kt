package com.smartplant.feature_auth.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.smartplant.feature_auth.databinding.FragmentRegsisterBinding
import com.smartplant.feature_auth.ui.AuthViewModel
import kotlinx.coroutines.launch
import kotlin.getValue

class AuthRegisterFragment : Fragment() {
    private val viewModel: AuthViewModel by activityViewModels()

    private var _binding: FragmentRegsisterBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentRegsisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupInputs()
        subscribeToFormErrors()
    }

    private fun setupInputs() {
        binding.usernameEditText.doAfterTextChanged { viewModel.onUsernameChanged(it.toString()) }
        binding.passwordEditText.doAfterTextChanged { viewModel.onPasswordChanged(it.toString()) }
        binding.passwordConfirmationEditText.doAfterTextChanged { viewModel.onPasswordConfirmChanged(it.toString()) }
    }

    private fun subscribeToFormErrors() {
        val activity = requireActivity()

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.formErrors.collect { errors ->
                    if (errors.username != null) binding.usernameInputLayout.setError(activity.getString(errors.username))
                    if (errors.password != null) binding.passwordInputLayout.setError(activity.getString(errors.password))
                    if (errors.confirmPassword != null) binding.passwordConfirmationInputLayout.setError(activity.getString(errors.confirmPassword))
                }
            }
        }
    }
}
