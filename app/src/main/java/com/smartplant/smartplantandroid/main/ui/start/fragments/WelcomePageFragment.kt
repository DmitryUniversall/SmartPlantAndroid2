package com.smartplant.smartplantandroid.main.ui.start.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.smartplant.smartplantandroid.databinding.FragmentStartWelcomeBinding

class WelcomePageFragment : Fragment() {
    private var _binding: FragmentStartWelcomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentStartWelcomeBinding.inflate(inflater, container, false)
        return binding.root
    }
}
