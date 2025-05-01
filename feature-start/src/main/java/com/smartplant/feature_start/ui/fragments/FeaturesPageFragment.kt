package com.smartplant.feature_start.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.smartplant.feature_start.databinding.FragmentFeaturesBinding

class FeaturesPageFragment : Fragment() {
    private var _binding: FragmentFeaturesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentFeaturesBinding.inflate(inflater, container, false)
        return binding.root
    }
}
