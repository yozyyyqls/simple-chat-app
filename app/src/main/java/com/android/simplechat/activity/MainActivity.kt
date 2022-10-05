package com.android.simplechat.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.android.simplechat.view.VerificationPageView

class MainActivity : AppCompatActivity() {
    private lateinit var verificationView: VerificationPageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        verificationView = VerificationPageView(this)
        setContentView(verificationView)
    }
}
