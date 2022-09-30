package com.android.simplechat

import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.text.InputType
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView

class VerificationView constructor(
    context: Context
) : RelativeLayout(context) {
    private val toolbar: Toolbar
    private val tvVerificationTitle: TextView
    private val tvVerificationContent: TextView
    private val cvPhoneInputContainer: CardView
    private val llPhoneInputContainer: LinearLayout
    private val etInputPhone: EditText
    private val btPhoneConfirm: Button

    init {
        // Toolbar
        val inflater = LayoutInflater.from(context)
        toolbar = inflater.inflate(R.layout.toolbar, null) as Toolbar
        toolbar.apply {
            layoutParams = LayoutParams(MATCH_PARENT, WRAP_CONTENT).apply {
                addRule(ALIGN_PARENT_TOP)
            }
            id = generateViewId()
        }
        this.addView(toolbar)

        // Title
        tvVerificationTitle = TextView(context).apply {
            text = context.getString(R.string.text_verification_title)
            layoutParams = LayoutParams(MATCH_PARENT, WRAP_CONTENT).apply {
                addRule(BELOW, toolbar.id)
                topMargin = 260
            }
            gravity = Gravity.CENTER
            setTextColor(context.getColor(R.color.colorPrimary))
            textSize = 32.0f
            typeface = Typeface.DEFAULT_BOLD
            id = generateViewId()
        }
        this.addView(tvVerificationTitle)

        // Content
        tvVerificationContent = TextView(context).apply {
            text = context.getString(R.string.text_verification_content)
            layoutParams = LayoutParams(MATCH_PARENT, WRAP_CONTENT).apply {
                addRule(BELOW, tvVerificationTitle.id)
            }
            gravity = Gravity.CENTER
            setTextAppearance(R.style.SimpleChatText)
            textSize = 18.0f
            id = generateViewId()
        }
        this.addView(tvVerificationContent)

        // Phone Number Input EditText
        etInputPhone = EditText(context).apply {
            layoutParams = LayoutParams(MATCH_PARENT, WRAP_CONTENT).apply {
                bottomMargin = 32
            }
            setPadding(16, 32, 16, 32)
            inputType = InputType.TYPE_CLASS_PHONE
            hint = context.getString(R.string.edit_text_hint)
            textSize = 20f
            val roundedShape = GradientDrawable().apply {
                cornerRadius = 16f
                setColor(context.getColor(R.color.white))
            }
            background = roundedShape
        }

        // Confirm Button
        btPhoneConfirm = Button(context).apply {
            layoutParams = LayoutParams(MATCH_PARENT, WRAP_CONTENT)
            text = context.getString(R.string.button_continue)
            textSize = 16f
            typeface = Typeface.DEFAULT_BOLD
            val roundedShape = GradientDrawable().apply {
                cornerRadius = 16f
                setColor(context.getColor(R.color.colorPrimary))
            }
            background = roundedShape
            setOnClickListener {
                val intent = Intent(context, OTPActivity::class.java)
                context.startActivity(intent)
            }
        }

        // LinearLayout of Phone Input Container
        llPhoneInputContainer = LinearLayout(context).apply {
            layoutParams = LayoutParams(MATCH_PARENT, WRAP_CONTENT).apply {
                setPadding(56, 64, 56, 64)
            }
            orientation = LinearLayout.VERTICAL
            addView(etInputPhone)
            addView(btPhoneConfirm)
        }

        cvPhoneInputContainer = CardView(context).apply {
            layoutParams = LayoutParams(MATCH_PARENT, WRAP_CONTENT).apply {
                addRule(BELOW, tvVerificationContent.id)
                addRule(RelativeLayout.CENTER_HORIZONTAL)
                setMargins(56, 200, 56, 200)
            }
            setCardBackgroundColor(context.getColor(R.color.colorPrimaryContainer))
            radius = 16f
            cardElevation = 16f
            id = generateViewId()
            addView(llPhoneInputContainer)
        }
        this.addView(cvPhoneInputContainer)
    }
}