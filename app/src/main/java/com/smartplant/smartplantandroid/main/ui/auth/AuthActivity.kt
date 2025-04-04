package com.smartplant.smartplantandroid.main.ui.auth

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class AuthActivity : AppCompatActivity() {
    enum class AuthType : java.io.Serializable {
        LOGIN, REGISTER
    }

    companion object {
        const val EXTRA_AUTH_TYPE = "EXTRA_AUTH_TYPE"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
}