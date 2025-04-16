package com.smartplant.smartplantandroid.main.ui.start.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.smartplant.smartplantandroid.databinding.FragmentStartFeaturesBinding

class FeaturesPageFragment : Fragment() {
    private var _binding: FragmentStartFeaturesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentStartFeaturesBinding.inflate(inflater, container, false)
        return binding.root
    }
}
