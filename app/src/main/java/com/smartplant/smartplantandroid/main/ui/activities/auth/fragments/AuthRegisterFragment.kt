package com.smartplant.smartplantandroid.main.ui.activities.auth.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.smartplant.smartplantandroid.databinding.FragmentAuthRegsisterBinding

class AuthRegisterFragment : Fragment() {
    private var _binding: FragmentAuthRegsisterBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentAuthRegsisterBinding.inflate(inflater, container, false)
        return binding.root
    }
}
