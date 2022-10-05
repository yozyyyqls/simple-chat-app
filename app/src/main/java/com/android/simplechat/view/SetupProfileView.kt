package com.android.simplechat.view

import android.content.Context
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.LinearLayout
import android.widget.TextView

class SetupProfileView(
    context: Context
) : LinearLayout(context) {
    private val tx: TextView
    init {
        this.apply {
            orientation = VERTICAL
            layoutParams = LayoutParams(MATCH_PARENT, MATCH_PARENT)
        }
        tx = TextView(context).apply {
            text = "Hello"
        }
        this.addView(tx)
    }
}
