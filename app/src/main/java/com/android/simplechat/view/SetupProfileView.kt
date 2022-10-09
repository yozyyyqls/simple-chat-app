package com.android.simplechat.view

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.text.InputType
import android.util.TypedValue
import android.view.Gravity
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.android.simplechat.R
import de.hdodenhof.circleimageview.CircleImageView

class SetupProfileView(
    context: Context
) : LinearLayout(context) {

    public val circleImageView: CircleImageView
    private val txProfileInfo: TextView
    private val txProfileContent: TextView
    private val cvNameInputContainer: CardView
    private val llNameInputContainer: LinearLayout
    public val etNameInput: EditText
    public val btContinue: Button

    init {
        this.apply {
            orientation = VERTICAL
            layoutParams = LayoutParams(MATCH_PARENT, MATCH_PARENT)
        }

        circleImageView = CircleImageView(context).apply {
            layoutParams = LayoutParams(WRAP_CONTENT, WRAP_CONTENT).apply {
                topMargin = 150
                gravity = Gravity.CENTER
            }
            setImageDrawable(context.getDrawable(R.drawable.pic_profile))
        }
        this.addView(circleImageView)

        txProfileInfo = TextView(context).apply {
            layoutParams = LayoutParams(MATCH_PARENT, WRAP_CONTENT)
            gravity = Gravity.CENTER
            text = context.getString(R.string.text_profile_page_title)
            setTextAppearance(R.style.SimpleChatTitleText)
        }
        this.addView(txProfileInfo)

        txProfileContent = TextView(context).apply {
            layoutParams = LayoutParams(MATCH_PARENT, WRAP_CONTENT)
            gravity = Gravity.CENTER
            text = context.getString(R.string.text_profile_page_content)
            setTextAppearance(R.style.SimpleChatContentText)
        }
        this.addView(txProfileContent)

        etNameInput = EditText(context).apply {
            layoutParams = LayoutParams(MATCH_PARENT, WRAP_CONTENT).apply {
                topMargin = 16
            }
            hint = "Input the user name"
            inputType = InputType.TYPE_CLASS_TEXT
        }

        btContinue = Button(context).apply {
            layoutParams = LayoutParams(WRAP_CONTENT, WRAP_CONTENT).apply {
                topMargin = 32
                gravity = Gravity.CENTER
            }
            text = context.getString(R.string.button_continue)
            val roundedShape = GradientDrawable().apply {
                cornerRadius = 16f
                val typedValue = TypedValue()
                context.theme.resolveAttribute(R.attr.onTextColor, typedValue, true)
                setTextColor(typedValue.data)
            }
            background = roundedShape
        }

        llNameInputContainer = LinearLayout(context).apply {
            layoutParams = LayoutParams(MATCH_PARENT, WRAP_CONTENT)
            orientation = VERTICAL
            addView(etNameInput)
            addView(btContinue)
        }

        cvNameInputContainer = CardView(context).apply {
            layoutParams = LayoutParams(MATCH_PARENT, WRAP_CONTENT).apply {
                setMargins(64, 32, 64, 32)
            }

            val typedValue = TypedValue()
            context.theme.resolveAttribute(R.attr.cardColor, typedValue, true)
            setCardBackgroundColor(typedValue.data)

            radius = 16f
            cardElevation = 16f

            addView(llNameInputContainer)
        }
        this.addView(cvNameInputContainer)
    }
}
