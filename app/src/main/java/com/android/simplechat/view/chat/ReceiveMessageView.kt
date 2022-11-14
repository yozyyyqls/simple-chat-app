package com.android.simplechat.view.chat

import android.content.Context
import android.graphics.Typeface
import android.view.Gravity
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.LinearLayout
import android.widget.TextView
import com.android.simplechat.R
import com.android.simplechat.utils.dp2px

class ReceiveMessageView constructor(
    context: Context
) : MessageView(context) {
    private var llBubble: LinearLayout? = null
    private var tvMessage: TextView? = null

    var message: String
        get() = tvMessage?.text.toString()
        set(value) {
            tvMessage!!.text = value
        }

    init {
        orientation = VERTICAL
        layoutParams = LayoutParams(MATCH_PARENT, WRAP_CONTENT)

        tvMessage = TextView(context).apply {
            layoutParams = LayoutParams(MATCH_PARENT, WRAP_CONTENT)
            setTextColor(context.getColor(R.color.white))
            typeface = Typeface.DEFAULT_BOLD
            textSize = 16f
            textAlignment = TEXT_ALIGNMENT_TEXT_START
        }

        llBubble = LinearLayout(context).apply {
            orientation = HORIZONTAL
            background = context.getDrawable(R.drawable.receive_msg_bubble)
            layoutParams = LayoutParams(WRAP_CONTENT, WRAP_CONTENT).apply {
                val padding = dp2px(15f)
                setPadding(padding, padding, padding, padding)
                leftMargin = dp2px(16f)
                rightMargin = dp2px(16f)
                topMargin = dp2px(4f)
                bottomMargin = dp2px(4f)
                gravity = Gravity.START
            }
            addView(tvMessage)
        }
        this.addView(llBubble)
    }
}
