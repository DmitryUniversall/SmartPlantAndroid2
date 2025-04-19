package com.smartplant.smartplantandroid.main.ui.activities.auth.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.smartplant.smartplantandroid.databinding.FragmentAuthLoginBinding

class AuthLoginFragment : Fragment() {
    private var _binding: FragmentAuthLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentAuthLoginBinding.inflate(inflater, container, false)
        return binding.root
    }
}
