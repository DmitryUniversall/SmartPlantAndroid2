package com.smartplant.smartplantandroid.main.ui.auth

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.smartplant.smartplantandroid.databinding.ActivityAuthBinding

class AuthActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAuthBinding

    enum class AuthType : java.io.Serializable {
        LOGIN, REGISTER
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}