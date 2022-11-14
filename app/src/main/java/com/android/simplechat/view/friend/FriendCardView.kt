package com.android.simplechat.view.friend

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.android.simplechat.R
import de.hdodenhof.circleimageview.CircleImageView

class FriendCardView constructor(
    context: Context
) : LinearLayout(context) {
    private var cvFriend: CardView? = null
    private var civUserProfile: CircleImageView? = null
    private var llNameContainer: LinearLayout? = null
    private var txName: TextView? = null
    private var txLatestMessage: TextView? = null

    var profile: CircleImageView
        get() = civUserProfile
        set(value) {
            civUserProfile = value
        }

    var name: String
        get() = txName?.text?.toString() ?: ""
        set(value) {
            txName!!.text = value
        }

    var latestMsg: String
        get() = txLatestMessage?.text?.toString() ?: ""
        set(value) {
            txLatestMessage!!.text = value
        }

    init {
        this.orientation = HORIZONTAL
        this.layoutParams = LayoutParams(MATCH_PARENT, WRAP_CONTENT)

        cvFriend = CardView(context).apply {
            layoutParams = LayoutParams(MATCH_PARENT, 56).apply {
                leftMargin = 16
                rightMargin = 16
                topMargin = 4
                bottomMargin = 4
            }
            radius = 8f
        }
        this.addView(cvFriend)

        civUserProfile = CircleImageView(context).apply {
            layoutParams = LayoutParams(dp2px(40f), dp2px(40f)).apply {
                leftMargin = 16
                topMargin = 8
                bottomMargin = 8
            }
        }
        llContainer!!.addView(civUserProfile)

        txName = TextView(context).apply {
            text = ""
            textSize = 12f
            layoutParams = LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
        }
        txLatestMessage = TextView(context).apply {
            text = "Latest Message"
            textSize = 10f
            layoutParams = LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
        }

        llNameContainer = LinearLayout(context).apply {
            orientation = VERTICAL
            layoutParams = LayoutParams(MATCH_PARENT, WRAP_CONTENT)
            addView(txName)
            addView(txLatestMessage)
        }
        this.addView(llNameContainer)
    }
}
