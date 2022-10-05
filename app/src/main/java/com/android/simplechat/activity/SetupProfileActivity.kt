package com.android.simplechat.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.android.simplechat.view.SetupProfileView

class SetupProfileActivity : AppCompatActivity() {
    private lateinit var setupProfileView: SetupProfileView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupProfileView = SetupProfileView(this@SetupProfileActivity)
        setContentView(setupProfileView)
    }
}
