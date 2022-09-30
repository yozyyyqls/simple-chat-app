package com.android.simplechat

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class OTPActivity : AppCompatActivity() {
    private lateinit var otpPageView: OtpPageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        otpPageView = OtpPageView(this)
        setContentView(otpPageView)
    }
}