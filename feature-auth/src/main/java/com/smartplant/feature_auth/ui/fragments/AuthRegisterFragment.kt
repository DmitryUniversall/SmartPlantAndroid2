package com.smartplant.feature_auth.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.smartplant.feature_auth.databinding.FragmentRegsisterBinding

class AuthRegisterFragment : Fragment() {
    private var _binding: FragmentRegsisterBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentRegsisterBinding.inflate(inflater, container, false)
        return binding.root
    }
}
