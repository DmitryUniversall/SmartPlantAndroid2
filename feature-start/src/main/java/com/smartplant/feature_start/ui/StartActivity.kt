package com.smartplant.feature_start.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewpager2.widget.ViewPager2
import com.smartplant.feature_start.databinding.ActivityStartBinding
import kotlinx.coroutines.launch
import com.smartplant.feature_start.R
import com.smartplant.core_android.R as CoreR

class StartActivity : AppCompatActivity() {
    private val viewModel: StartViewModel by viewModels()
    private lateinit var binding: ActivityStartBinding
    private lateinit var adapter: StartPageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = StartPageAdapter(this)
        binding.viewPager.adapter = adapter

        initPages()
        setupPager()
        setupButtons()
    }

    private fun initPages() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.pages.collect { pages ->
                    adapter.submitPages(pages)
                    binding.pageIndicator.setActive(0)
                }
            }
        }
    }

    private fun setupPager() {
        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                binding.skipButton.isVisible = position != adapter.itemCount - 1
                binding.nextButton.text = if (position == adapter.itemCount - 1) getText(R.string.get_started) else getText(CoreR.string.next)
            }
        })
    }

    private fun navigateToAuth() {
        startActivity(Intent().setClassName(this, "com.smartplant.feature_auth.ui.AuthActivity"))
    }

    private fun setupButtons() {
        binding.nextButton.setOnClickListener {
            val currentItem = binding.viewPager.currentItem
            if (currentItem >= adapter.itemCount - 1) {
                navigateToAuth()
                return@setOnClickListener
            }

            binding.viewPager.setCurrentItem(currentItem + 1, true)
            binding.pageIndicator.setActive(currentItem + 1)
        }

        binding.skipButton.setOnClickListener {
            binding.viewPager.setCurrentItem(adapter.itemCount - 1, true)
            binding.pageIndicator.setActive(adapter.itemCount - 1)
        }
    }
}
