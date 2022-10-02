package com.android.simplechat

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var verificationView: VerificationPageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        verificationView = VerificationPageView(this)
        setContentView(verificationView)
    }
}
