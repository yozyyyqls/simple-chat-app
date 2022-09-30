package com.android.simplechat

import android.content.Context
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.mukesh.OTP_VIEW_TYPE_BORDER
import com.mukesh.OtpView

class OtpPageView constructor(
    context: Context
) : LinearLayout(context) {
    private val tvOtpTitle: TextView
    private val tvOtpContent: TextView
    private val llInputOtpContainer: LinearLayout
    private val otpView: ComposeView
    private val btContinue: Button

    init {
        this.orientation = VERTICAL

        tvOtpTitle = TextView(context).apply {
            text = context.getString(R.string.text_otp_title)
            textSize = 32f
            typeface = Typeface.DEFAULT_BOLD
            gravity = Gravity.CENTER
            setTextColor(context.getColor(R.color.colorPrimary))
            layoutParams = LayoutParams(MATCH_PARENT, WRAP_CONTENT).apply {
                topMargin = 500
            }
        }
        this.addView(tvOtpTitle)

        tvOtpContent = TextView(context).apply {
            text = context.getString(R.string.text_otp_content)
            textSize = 18f
            setTextColor(context.getColor(R.color.black))
            gravity = Gravity.CENTER
            layoutParams = LayoutParams(MATCH_PARENT, WRAP_CONTENT).apply {
                topMargin = 16
            }
        }
        this.addView(tvOtpContent)

        val inflater = LayoutInflater.from(context)
        otpView = inflater.inflate(R.layout.otp_view, null) as ComposeView
        otpView.apply {
            setContent {
                MaterialTheme {
                    var otpValue by remember { mutableStateOf("") }
                    OtpView(
                        otpText = otpValue,
                        onOtpTextChange = {
                            otpValue = it
                            Log.d("Actual Value", otpValue)
                        },
                        type = OTP_VIEW_TYPE_BORDER,
                        password = false,
                        containerSize = 56.dp,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        charColor = Color(R.color.colorPrimary),
                    )
                }
            }
        }

        btContinue = Button(context).apply {
            textSize = 16f
            setTextColor(context.getColor(R.color.white))
            text = context.getString(R.string.button_otp_continue)
            typeface = Typeface.DEFAULT_BOLD
            val roundedShape = GradientDrawable().apply {
                cornerRadius = 16f
                setColor(context.getColor(R.color.colorPrimary))
            }
            background = roundedShape
            layoutParams = LayoutParams(MATCH_PARENT, WRAP_CONTENT).apply {
                topMargin = 32
            }
        }

        llInputOtpContainer = LinearLayout(context).apply {
            layoutParams = LayoutParams(MATCH_PARENT, WRAP_CONTENT).apply {
                setMargins(64, 200, 64, 200)
            }
            orientation = VERTICAL
            addView(otpView)
            addView(btContinue)
        }
        this.addView(llInputOtpContainer)
    }
}